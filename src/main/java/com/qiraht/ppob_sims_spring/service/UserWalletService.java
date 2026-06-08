package com.qiraht.ppob_sims_spring.service;

import com.qiraht.ppob_sims_spring.entity.User;
import com.qiraht.ppob_sims_spring.entity.UserWallet;
import com.qiraht.ppob_sims_spring.enums.UserStatus;
import com.qiraht.ppob_sims_spring.enums.UserWalletStatus;
import com.qiraht.ppob_sims_spring.exception.custom.ValidationException;
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

    public UserWallet getUserWalletByUser(User user) {
        if (!user.getStatus().equals(UserStatus.ACTIVE)) {
            throw new ValidationException("User tidak aktif/bermasalah");
        }

        return userWalletRepository.findByUser(user).orElseThrow(
                () -> new ValidationException("User tidak memiliki wallet"));
    }

    public BigDecimal toUpUserWalletBalance(User user, BigDecimal balance) {
        UserWallet wallet = getUserWalletByUser(user);

        wallet.setBalance(wallet.getBalance().add(balance));

        userWalletRepository.save(wallet);

        return wallet.getBalance();
    }
}
