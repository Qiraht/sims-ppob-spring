package com.qiraht.ppob_sims_spring.controller;

import com.qiraht.ppob_sims_spring.dto.ApiResponse;
import com.qiraht.ppob_sims_spring.dto.request.LoginRequest;
import com.qiraht.ppob_sims_spring.dto.response.LoginResponse;
import com.qiraht.ppob_sims_spring.service.AuthService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping
@Tag(name = "1. Module Membership")
@Validated
public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<ApiResponse<LoginResponse>> postLoginController(@Valid @RequestBody LoginRequest request) {
        String token = authService.loginUser(request);

        return ResponseEntity.ok(ApiResponse.success("Login Sukses", new LoginResponse(token)));
    }
}
