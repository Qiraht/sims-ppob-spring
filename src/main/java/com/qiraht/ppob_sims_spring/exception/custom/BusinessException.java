package com.qiraht.ppob_sims_spring.exception.custom;

public class BusinessException extends RuntimeException{
    public BusinessException(String message) {
        super(message);
    }
}
