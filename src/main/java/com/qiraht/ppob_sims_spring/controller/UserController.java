package com.qiraht.ppob_sims_spring.controller;

import com.qiraht.ppob_sims_spring.dto.ApiResponse;
import com.qiraht.ppob_sims_spring.dto.request.RegisterRequest;
import com.qiraht.ppob_sims_spring.service.UserService;
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
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/registration")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<ApiResponse<Void>> postRegisterController(@Valid @RequestBody RegisterRequest request) {
        userService.registerUser(request);

        return ResponseEntity.status(HttpStatus.CREATED.value()).body(ApiResponse.success("Registrasi berhasil dilakukan", null));
    }
}
