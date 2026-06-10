package com.qiraht.ppob_sims_spring.service;

import com.qiraht.ppob_sims_spring.config.CustomUserDetails;
import com.qiraht.ppob_sims_spring.dto.CurrentUserPayload;
import com.qiraht.ppob_sims_spring.exception.custom.UnAuthorizedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class CurrentUserService {
    public CurrentUserPayload getCurrentUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth == null || !auth.isAuthenticated()) {
            throw new UnAuthorizedException("Token tidak valid atau kadaluwarsa");
        }

        Object principal = auth.getPrincipal();

        if (principal instanceof CustomUserDetails userDetails) {
            return new CurrentUserPayload(
                    userDetails.getId(),
                    userDetails.getUsername(),
                    userDetails.getAuthorities().stream()
                            .map(GrantedAuthority::getAuthority)
                            .toList()
            );
        }

        throw new UnAuthorizedException("Token tidak valid atau kadaluwarsa");
    }
}
