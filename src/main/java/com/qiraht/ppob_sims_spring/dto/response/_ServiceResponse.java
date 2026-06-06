package com.qiraht.ppob_sims_spring.dto.response;

import java.math.BigDecimal;

public record _ServiceResponse(
        String service_code,
        String service_name,
        String Service_icon,
        BigDecimal service_tariff
) {
}
