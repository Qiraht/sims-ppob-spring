package com.qiraht.ppob_sims_spring.service;

import com.qiraht.ppob_sims_spring.dto.CurrentUserPayload;
import com.qiraht.ppob_sims_spring.dto.request.RegisterRequest;
import com.qiraht.ppob_sims_spring.dto.request.UpdateProfileRequest;
import com.qiraht.ppob_sims_spring.entity.User;
import com.qiraht.ppob_sims_spring.enums.UserStatus;
import com.qiraht.ppob_sims_spring.exception.custom.NotFoundException;
import com.qiraht.ppob_sims_spring.exception.custom.ValidationException;
import com.qiraht.ppob_sims_spring.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final CurrentUserService currentUserService;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, CurrentUserService currentUserService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.currentUserService = currentUserService;
    }

    public User registerUser(RegisterRequest request) {
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

        return user;
    }

    public User getUserById(UUID userId) {
        return userRepository.findById(userId).orElseThrow(() -> new NotFoundException("User tidak ditemukan"));
    }

    public User getAuthenticatedUser() {
        CurrentUserPayload currentUser = currentUserService.getCurrentUser();

        return getUserById(currentUser.userId());
    }

    public User updateUserProfile(UpdateProfileRequest request) {
        User user = getAuthenticatedUser();

        user.setFirstName(request.first_name());
        user.setLastName(request.last_name());

        userRepository.save(user);

        return user;
    }
}
