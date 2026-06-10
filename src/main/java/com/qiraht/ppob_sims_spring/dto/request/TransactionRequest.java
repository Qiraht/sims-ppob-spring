package com.qiraht.ppob_sims_spring.dto.request;

import jakarta.validation.constraints.NotBlank;

public record TransactionRequest(
        @NotBlank
        String service_code
) {
}
