package com.qiraht.ppob_sims_spring.service;

import com.qiraht.ppob_sims_spring.dto.request.RegisterRequest;
import com.qiraht.ppob_sims_spring.entity.User;
import com.qiraht.ppob_sims_spring.enums.UserStatus;
import com.qiraht.ppob_sims_spring.exception.custom.ValidationException;
import com.qiraht.ppob_sims_spring.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final UserWalletService userWalletService;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, UserWalletService userWalletService, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.userWalletService = userWalletService;
        this.passwordEncoder = passwordEncoder;
    }

    public void registerUser(RegisterRequest request) {
        if (userRepository.existsByEmail(request.email())) {
            throw new ValidationException("Email sudah terdaftar");
        }

        String hashedPassword = passwordEncoder.encode(request.password());

        User user = User.builder()
                .email(request.email())
                .password(hashedPassword)
                .firstName(request.first_name())
                .lastName(request.last_name())
                .status(UserStatus.ACTIVE)
                .isDeleted(false)
                .build();

        userRepository.save(user);

        userWalletService.createUserWallet(user);
    }
}
