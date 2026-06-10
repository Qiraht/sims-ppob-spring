package com.qiraht.ppob_sims_spring.dto.response;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

public record TransactionResponse(
        String invoice_number,
        String service_code,
        String service_name,
        String transaction_type,
        BigDecimal total_amount,
        OffsetDateTime created_on
) {
}
