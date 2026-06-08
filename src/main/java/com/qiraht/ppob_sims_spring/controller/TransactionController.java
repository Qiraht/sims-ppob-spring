package com.qiraht.ppob_sims_spring.controller;

import com.qiraht.ppob_sims_spring.dto.ApiResponse;
import com.qiraht.ppob_sims_spring.dto.request.TopUpRequest;
import com.qiraht.ppob_sims_spring.dto.response.BalanceResponse;
import com.qiraht.ppob_sims_spring.service.UserService;
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
    private final UserService userService;

    public TransactionController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/balance")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("authenticated()")
    public ResponseEntity<ApiResponse<BalanceResponse>> getBalanceController() {
        BigDecimal data = userService.getUserBalance();

        return ResponseEntity.ok(ApiResponse.success("Get Balance Berhasil", new BalanceResponse(data)));
    }

    @PostMapping("/topup")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("authenticated()")
    public ResponseEntity<ApiResponse<BalanceResponse>> postTopUpController(@Valid @RequestBody TopUpRequest request) {
        BigDecimal data = userService.topUpUserBalance(request.top_up_amount());

        return ResponseEntity.ok(ApiResponse.success("Top Up Berhasil", new BalanceResponse(data)));
    }

}
