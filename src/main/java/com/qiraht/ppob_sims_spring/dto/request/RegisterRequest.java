package com.qiraht.ppob_sims_spring.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record RegisterRequest(
        @Email
        @NotBlank
        String email,
        @NotBlank
        String first_name,
        @NotBlank
        String last_name,
        @NotBlank
        String password
) {
}
