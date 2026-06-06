package com.qiraht.ppob_sims_spring.service;

import com.qiraht.ppob_sims_spring.entity.User;
import com.qiraht.ppob_sims_spring.entity.UserWallet;
import com.qiraht.ppob_sims_spring.enums.UserWalletStatus;
import com.qiraht.ppob_sims_spring.repository.UserWalletRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class UserWalletService {
    private final UserWalletRepository userWalletRepository;

    public UserWalletService(UserWalletRepository userWalletRepository) {
        this.userWalletRepository = userWalletRepository;
    }

    public void createUserWallet(User user) {
        UserWallet wallet = UserWallet.builder()
                .user(user)
                .balance(BigDecimal.ZERO)
                .status(UserWalletStatus.ACTIVE)
                .build();

        userWalletRepository.save(wallet);
    }
}
