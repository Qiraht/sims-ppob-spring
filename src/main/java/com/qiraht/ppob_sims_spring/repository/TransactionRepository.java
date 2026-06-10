package com.qiraht.ppob_sims_spring.repository;

import com.qiraht.ppob_sims_spring.entity.Transaction;
import com.qiraht.ppob_sims_spring.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface TransactionRepository extends JpaRepository<Transaction, UUID> {
    Page<Transaction> findAllByUser(User user, Pageable pageable);
}
