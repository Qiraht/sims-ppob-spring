package com.qiraht.ppob_sims_spring.dto.response;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

public record TransactionHistoryResponse(
        String invoice_number,
        String transaction_type,
        String description,
        BigDecimal total_amount,
        OffsetDateTime created_on
) {
}
