package com.qiraht.ppob_sims_spring.service;

import com.qiraht.ppob_sims_spring.dto.CurrentUserPayload;
import com.qiraht.ppob_sims_spring.dto.request.RegisterRequest;
import com.qiraht.ppob_sims_spring.dto.request.UpdateProfileRequest;
import com.qiraht.ppob_sims_spring.entity.User;
import com.qiraht.ppob_sims_spring.enums.UserStatus;
import com.qiraht.ppob_sims_spring.exception.custom.NotFoundException;
import com.qiraht.ppob_sims_spring.exception.custom.ValidationException;
import com.qiraht.ppob_sims_spring.repository.UserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import jakarta.annotation.PostConstruct;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Set;
import java.util.UUID;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final CurrentUserService currentUserService;

    @Value("${app.upload.profile-images}")
    private String profileImagesPath;

    private static final Set<String> ALLOWED_CONTENT_TYPES = Set.of("image/jpeg", "image/jpg");

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, CurrentUserService currentUserService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.currentUserService = currentUserService;
    }

    @PostConstruct
    public void init() {
        try {
            Files.createDirectories(Paths.get(profileImagesPath));
        } catch (IOException e) {
            throw new RuntimeException("Could not create upload directory", e);
        }
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

    public User updateProfileImage(MultipartFile file) {
        if (file.isEmpty()) {
            throw new ValidationException("File tidak boleh kosong");
        }

        String contentType = file.getContentType();
        if (contentType == null || !ALLOWED_CONTENT_TYPES.contains(contentType.toLowerCase())) {
            throw new ValidationException("Format file tidak valid. Hanya file JPG/JPEG yang diizinkan");
        }

        User user = getAuthenticatedUser();

        String filename = UUID.randomUUID() + ".jpg";
        Path filePath = Paths.get(profileImagesPath, filename);

        try {
            Files.copy(file.getInputStream(), filePath);
        } catch (IOException e) {
            throw new RuntimeException("Gagal menyimpan file", e);
        }

        user.setProfileImage("/profile-uploads/" + filename);
        userRepository.save(user);

        return user;
    }
}
