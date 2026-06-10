package com.qiraht.ppob_sims_spring.helper;

import com.qiraht.ppob_sims_spring.dto.TransactionData;
import com.qiraht.ppob_sims_spring.entity.Transaction;

public class TransactionMapper {
    public static TransactionData toTransactionData(Transaction transaction) {
        return new TransactionData(
                transaction.getInvoiceNumber(),
                transaction.getTransactionType().name(),
                transaction.getDescription(),
                transaction.getTotalAmount(),
                transaction.getCreatedAt()
        );
    }
}
