package com.qiraht.ppob_sims_spring.service;

import com.qiraht.ppob_sims_spring.entity.User;
import com.qiraht.ppob_sims_spring.entity.UserWallet;
import com.qiraht.ppob_sims_spring.enums.UserWalletStatus;
import com.qiraht.ppob_sims_spring.exception.custom.ValidationException;
import com.qiraht.ppob_sims_spring.repository.UserWalletRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class UserWalletService {
    private final UserWalletRepository userWalletRepository;
    private final CurrentUserService currentUserService;


    public UserWalletService(UserWalletRepository userWalletRepository, CurrentUserService currentUserService) {
        this.userWalletRepository = userWalletRepository;
        this.currentUserService = currentUserService;
    }

    public void createUserWallet(User user) {
        UserWallet wallet = UserWallet.builder()
                .user(user)
                .balance(BigDecimal.ZERO)
                .status(UserWalletStatus.ACTIVE)
                .build();

        userWalletRepository.save(wallet);
    }

    public UserWallet getAuthenticatedtUserWallet() {
        java.util.UUID userId = currentUserService.getCurrentUser().userId();

        UserWallet userWallet = userWalletRepository.findByUserId(userId).orElseThrow(
                () -> new ValidationException("User tidak memiliki wallet"));

        if (!userWallet.getStatus().equals(UserWalletStatus.ACTIVE)) {
            throw new ValidationException("Wallet tidak aktif/bermasalah");

        }

        return userWallet;
    }

    public BigDecimal toUpUserWalletBalance(User user, BigDecimal balance) {
        UserWallet wallet = userWalletRepository.findByUser(user).orElseThrow(
                () -> new ValidationException("User tidak memiliki wallet")
        );

        if (!wallet.getStatus().equals(UserWalletStatus.ACTIVE)) {
            throw new ValidationException("Wallet tidak aktif/bermasalah");
        }

        wallet.setBalance(wallet.getBalance().add(balance));

        userWalletRepository.save(wallet);

        return wallet.getBalance();
    }

    @Transactional
    public void userWalletTransactions(User user, BigDecimal balance) {
        UserWallet wallet = userWalletRepository.findByUserForUpdate(user).orElseThrow(
                () -> new ValidationException("User tidak memiliki wallet")
        );

        if (!wallet.getStatus().equals(UserWalletStatus.ACTIVE)) {
            throw new ValidationException("Wallet tidak aktif/bermasalah");
        }

        if (wallet.getBalance().compareTo(balance) < 0) {
            throw new ValidationException("Saldo tidak mencukupi");
        }

        wallet.setBalance(wallet.getBalance().subtract(balance));

        userWalletRepository.save(wallet);
    }
}
