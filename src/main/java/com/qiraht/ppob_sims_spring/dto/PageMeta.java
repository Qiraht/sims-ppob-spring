package com.qiraht.ppob_sims_spring.dto;

public record PageMeta(
        int page,
        int limit,
        int offset,
        long total_elements,
        int total_pages,
        boolean has_next,
        boolean has_previous) {
}
