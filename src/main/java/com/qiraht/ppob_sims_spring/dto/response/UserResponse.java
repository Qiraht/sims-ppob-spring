package com.qiraht.ppob_sims_spring.dto.response;

public record UserResponse(
        String email,
        String first_name,
        String last_name,
        String profile_image
) {
    public UserResponse() {
        this(null, null, null, null);
    }
}
