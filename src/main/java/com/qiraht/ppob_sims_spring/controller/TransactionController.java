package com.qiraht.ppob_sims_spring.controller;

import com.qiraht.ppob_sims_spring.dto.ApiResponse;
import com.qiraht.ppob_sims_spring.dto.PageMeta;
import com.qiraht.ppob_sims_spring.dto.TransactionData;
import com.qiraht.ppob_sims_spring.dto.request.TopUpRequest;
import com.qiraht.ppob_sims_spring.dto.request.TransactionRequest;
import com.qiraht.ppob_sims_spring.dto.response.BalanceResponse;
import com.qiraht.ppob_sims_spring.dto.response.TransactionHistoryResponse;
import com.qiraht.ppob_sims_spring.dto.response.TransactionResponse;
import com.qiraht.ppob_sims_spring.entity.Transaction;
import com.qiraht.ppob_sims_spring.helper.TransactionMapper;
import com.qiraht.ppob_sims_spring.service.TransactionService;
import com.qiraht.ppob_sims_spring.service.UserWalletService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping
@Tag(name = "3. Module Transaction")
@Validated
public class TransactionController {
    private final UserWalletService userWalletService;
    private final TransactionService transactionService;

    public TransactionController(UserWalletService userWalletService, TransactionService transactionService) {
        this.userWalletService = userWalletService;
        this.transactionService = transactionService;
    }

    @GetMapping("/balance")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("authenticated()")
    public ResponseEntity<ApiResponse<BalanceResponse>> getBalanceController() {
        BigDecimal data = userWalletService.getAuthenticatedtUserWallet().getBalance();

        return ResponseEntity.ok(ApiResponse.success("Get Balance Berhasil", new BalanceResponse(data)));
    }

    @PostMapping("/topup")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("authenticated()")
    public ResponseEntity<ApiResponse<BalanceResponse>> postTopUpController(@Valid @RequestBody TopUpRequest request) {
        BigDecimal data = transactionService.topUpUserTransaction(request.top_up_amount());

        return ResponseEntity.ok(ApiResponse.success("Top Up Berhasil", new BalanceResponse(data)));
    }

    @PostMapping("/transaction")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("authenticated()")
    public ResponseEntity<ApiResponse<TransactionResponse>> postTransactionController(@Valid @RequestBody TransactionRequest request) {
        Transaction data = transactionService.createNewPaymentTransaction(request);

        return ResponseEntity.ok(ApiResponse.success("Transaksi Berhasil", new TransactionResponse(
                data.getInvoiceNumber(),
                data.getService().getCode(),
                data.getService().getName(),
                data.getTransactionType().name(),
                data.getTotalAmount(),
                data.getCreatedAt()
        )));
    }

    @GetMapping("/transaction/history")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("authenticated()")
    public ResponseEntity<ApiResponse<TransactionHistoryResponse>> getTransactionHistoryController(@ParameterObject @PageableDefault(sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {
        Page<Transaction> data = transactionService.getUserTransactions(pageable);

        List<TransactionData> records = data.getContent().stream().map(TransactionMapper::toTransactionData).toList();

        PageMeta meta = new PageMeta(
                data.getNumber(),
                data.getSize(),
                data.getSize() * data.getNumber(),
                data.getTotalElements(),
                data.getTotalPages(),
                data.hasNext(),
                data.hasPrevious()
        );

        return ResponseEntity.ok(ApiResponse.success("success",
                new TransactionHistoryResponse(records, meta))
        );
    }

}
