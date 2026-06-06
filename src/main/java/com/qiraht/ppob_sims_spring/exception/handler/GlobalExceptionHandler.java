package com.qiraht.ppob_sims_spring.exception.handler;

import com.qiraht.ppob_sims_spring.dto.ApiResponse;
import com.qiraht.ppob_sims_spring.exception.custom.ValidationException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<ApiResponse<Void>> handleBusinessException(ValidationException ex, HttpServletRequest request) {
        ApiResponse<Void> body = ApiResponse.error(102, ex.getMessage());

        return ResponseEntity.badRequest().body(body);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<?>> handleBusinessException(MethodArgumentNotValidException ex, HttpServletRequest request) {
        String message = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .findFirst()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .orElse("Validation Failed");

        ApiResponse<?> body = ApiResponse.error(102, message);

        return ResponseEntity.badRequest().body(body);
    }


    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Void>> handleBusinessException(Exception ex, HttpServletRequest request) {
        ApiResponse<Void> body = ApiResponse.error(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Internal Server Error");

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(body);
    }
}
