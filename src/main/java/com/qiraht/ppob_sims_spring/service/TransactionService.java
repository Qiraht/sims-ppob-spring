package com.qiraht.ppob_sims_spring.service;

import com.qiraht.ppob_sims_spring.dto.request.TransactionRequest;
import com.qiraht.ppob_sims_spring.entity.Transaction;
import com.qiraht.ppob_sims_spring.entity.User;
import com.qiraht.ppob_sims_spring.entity._Service;
import com.qiraht.ppob_sims_spring.enums.TransactionType;
import com.qiraht.ppob_sims_spring.repository.TransactionRepository;
import com.qiraht.ppob_sims_spring.util.InvoiceGenerator;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class TransactionService {
    private final TransactionRepository transactionRepository;
    private final UserService userService;
    private final UserWalletService userWalletService;
    private final _ServiceService _serviceService;

    public TransactionService(TransactionRepository transactionRepository, UserService userService, UserWalletService userWalletService, _ServiceService _serviceService) {
        this.transactionRepository = transactionRepository;
        this.userService = userService;
        this.userWalletService = userWalletService;
        this._serviceService = _serviceService;
    }

    public BigDecimal topUpUserTransaction(BigDecimal amount) {
        User user = userService.getAuthenticatedUser();

        BigDecimal topup = userWalletService.toUpUserWalletBalance(user, amount);

        Transaction transaction = Transaction.builder()
                .invoiceNumber(InvoiceGenerator.generateInvoiceNumber())
                .user(user)
                .service(null)
                .transactionType(TransactionType.TOPUP)
                .description("Top Up Balance")
                .totalAmount(amount)
                .build();

        transactionRepository.save(transaction);

        return topup;
    }

    @Transactional
    public Transaction createNewPaymentTransaction(TransactionRequest request) {
        User user = userService.getAuthenticatedUser();

        _Service service = _serviceService.getServiceByCode(request.service_code());

        userWalletService.userWalletTransactions(user, service.getTariff());

        Transaction transaction = Transaction.builder()
                .invoiceNumber(InvoiceGenerator.generateInvoiceNumber())
                .user(user)
                .service(service)
                .transactionType(TransactionType.PAYMENT)
                .description(service.getName())
                .totalAmount(service.getTariff())
                .build();

        transactionRepository.save(transaction);

        return transaction;
    }

    public Page<Transaction> getUserTransactions(Pageable pageable) {
        User user = userService.getAuthenticatedUser();

        return transactionRepository.findAllByUser(user, pageable);
    }
}
