package com.qiraht.ppob_sims_spring.service;

import com.qiraht.ppob_sims_spring.config.CustomUserDetails;
import com.qiraht.ppob_sims_spring.dto.request.LoginRequest;
import com.qiraht.ppob_sims_spring.util.JwtUtil;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class AuthService {
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    public AuthService(AuthenticationManager authenticationManager, JwtUtil jwtUtil) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
    }

    public String loginUser(LoginRequest request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.email(), request.password())
        );

        CustomUserDetails userDetails = (CustomUserDetails) Objects.requireNonNull(
                authentication.getPrincipal(), "Principal should not be null");

        return jwtUtil.generateToken(userDetails);
    }
}
