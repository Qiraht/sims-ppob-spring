package com.qiraht.ppob_sims_spring.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record TopUpRequest(
        @Positive
        @Min(1000)
        BigDecimal top_up_amount
) {
}
