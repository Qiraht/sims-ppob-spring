package com.qiraht.ppob_sims_spring.exception.handler;

import com.qiraht.ppob_sims_spring.dto.ApiResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Void>> handleBusinessException(Exception ex, HttpServletRequest request) {
        ApiResponse<Void> body = ApiResponse.error(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Internal Server Error");

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(body);
    }
}
