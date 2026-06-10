package com.qiraht.ppob_sims_spring.repository;

import com.qiraht.ppob_sims_spring.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface TransactionRepository extends JpaRepository<Transaction, UUID> {
}
