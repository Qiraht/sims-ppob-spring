package com.qiraht.ppob_sims_spring.dto.response;

import com.qiraht.ppob_sims_spring.dto.PageMeta;
import com.qiraht.ppob_sims_spring.dto.TransactionData;

import java.util.List;

public record TransactionHistoryResponse(
        List<TransactionData> records,
        PageMeta meta
) {
}
