package com.qiraht.ppob_sims_spring.service;

import com.qiraht.ppob_sims_spring.entity.User;
import com.qiraht.ppob_sims_spring.entity.UserWallet;
import com.qiraht.ppob_sims_spring.enums.UserWalletStatus;
import com.qiraht.ppob_sims_spring.exception.custom.ValidationException;
import com.qiraht.ppob_sims_spring.repository.UserWalletRepository;
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

    public BigDecimal toUpUserWalletBalance(BigDecimal balance) {
        UserWallet wallet = getAuthenticatedtUserWallet();

        wallet.setBalance(wallet.getBalance().add(balance));

        userWalletRepository.save(wallet);

        return wallet.getBalance();
    }
}
