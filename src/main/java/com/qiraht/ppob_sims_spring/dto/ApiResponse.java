package com.qiraht.ppob_sims_spring.dto;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({"status", "message", "data"})
public record ApiResponse<T>(Integer status, String message, T data) {
    public static <T> ApiResponse<T> success(String message, T data) {
        return new ApiResponse<>(0, message, data);
    }

    public static <T> ApiResponse<T> success(String message) {
        return new ApiResponse<>(0, message, null);
    }

    public static <T> ApiResponse<T> error(Integer status,String message) {
        return new ApiResponse<>(status, message, null);
    }
}