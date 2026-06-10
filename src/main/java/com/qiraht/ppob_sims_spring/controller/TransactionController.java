package com.qiraht.ppob_sims_spring.controller;

import com.qiraht.ppob_sims_spring.dto.ApiResponse;
import com.qiraht.ppob_sims_spring.dto.request.TopUpRequest;
import com.qiraht.ppob_sims_spring.dto.request.TransactionRequest;
import com.qiraht.ppob_sims_spring.dto.response.BalanceResponse;
import com.qiraht.ppob_sims_spring.dto.response.TransactionResponse;
import com.qiraht.ppob_sims_spring.entity.Transaction;
import com.qiraht.ppob_sims_spring.service.TransactionService;
import com.qiraht.ppob_sims_spring.service.UserWalletService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

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

}
