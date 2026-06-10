package com.qiraht.ppob_sims_spring.dto;

import java.util.List;
import java.util.UUID;

public record CurrentUserPayload(UUID userId, String email, List<String> roles) {
}
