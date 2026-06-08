package com.qiraht.ppob_sims_spring.service;

import com.qiraht.ppob_sims_spring.config.CustomUserDetails;
import com.qiraht.ppob_sims_spring.dto.request.RegisterRequest;
import com.qiraht.ppob_sims_spring.dto.request.UpdateProfileRequest;
import com.qiraht.ppob_sims_spring.entity.User;
import com.qiraht.ppob_sims_spring.enums.UserStatus;
import com.qiraht.ppob_sims_spring.exception.custom.NotFoundException;
import com.qiraht.ppob_sims_spring.exception.custom.UnAuthorizedException;
import com.qiraht.ppob_sims_spring.exception.custom.ValidationException;
import com.qiraht.ppob_sims_spring.repository.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

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

    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow(() -> new NotFoundException("User tidak ditemukan"));
    }

    public User getAuthenticatedUserByEmail() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            throw new UnAuthorizedException("Token tidak valid atau kadaluwarsa");
        }

        Object principal = authentication.getPrincipal();

        if (principal instanceof CustomUserDetails userDetails) {
            return getUserByEmail(userDetails.getUsername());
        }

        throw new UnAuthorizedException("Token tidak valid atau kadaluwarsa");
    }

    public User updateUserProfile(UpdateProfileRequest request) {
        User user = getAuthenticatedUserByEmail();

        user.setFirstName(request.first_name());
        user.setLastName(request.last_name());

        userRepository.save(user);

        return getUserByEmail(user.getEmail());
    }

    public BigDecimal getUserBalance() {
        User user = getAuthenticatedUserByEmail();

        return userWalletService.getUserWalletByUser(user).getBalance();
    }

    public BigDecimal topUpUserBalance(BigDecimal amount) {
        User user = getAuthenticatedUserByEmail();

        return userWalletService.toUpUserWalletBalance(user, amount);
    }
}
