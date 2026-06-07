package com.qiraht.ppob_sims_spring.dto.request;

import jakarta.validation.constraints.NotBlank;

public record UpdateProfileRequest(
        @NotBlank
        String first_name,

        @NotBlank
        String last_name
) {
}
