package com.qiraht.ppob_sims_spring.controller;

import com.qiraht.ppob_sims_spring.dto.ApiResponse;
import com.qiraht.ppob_sims_spring.dto.request.RegisterRequest;
import com.qiraht.ppob_sims_spring.dto.request.UpdateProfileRequest;
import com.qiraht.ppob_sims_spring.dto.response.UserResponse;
import com.qiraht.ppob_sims_spring.entity.User;
import com.qiraht.ppob_sims_spring.service.UserService;
import com.qiraht.ppob_sims_spring.service.UserWalletService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping
@Tag(name = "1. Module Membership")
@Validated
public class UserController {
    private final UserService userService;
    private final UserWalletService userWalletService;

    public UserController(UserService userService, UserWalletService userWalletService) {
        this.userService = userService;
        this.userWalletService = userWalletService;
    }

    @PostMapping("/registration")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<ApiResponse<Void>> postRegisterController(@Valid @RequestBody RegisterRequest request) {
        User user = userService.registerUser(request);
        userWalletService.createUserWallet(user);

        return ResponseEntity.status(HttpStatus.CREATED.value()).body(ApiResponse.success("Registrasi berhasil dilakukan", null));
    }

    @GetMapping("/profile")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("authenticated()")
    public ResponseEntity<ApiResponse<UserResponse>> getProfileController() {
        User data = userService.getAuthenticatedUser();

        return ResponseEntity.ok(ApiResponse.success("success", new UserResponse(data.getEmail(), data.getFirstName(), data.getLastName(), data.getProfileImage())));
    }

    @PutMapping("/profile/update")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("authenticated()")
    public ResponseEntity<ApiResponse<UserResponse>> putProfileUpdateController(@Valid @RequestBody UpdateProfileRequest request) {
        User data = userService.updateUserProfile(request);

        return ResponseEntity.ok(ApiResponse.success("success", new UserResponse(data.getEmail(), data.getFirstName(), data.getLastName(), data.getProfileImage())));
    }
}
