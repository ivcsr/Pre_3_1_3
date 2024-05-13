package com.kata.pre_3_1_2_sb.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class Pre312Exception extends RuntimeException{
    private final HttpStatus httpStatus;

    public Pre312Exception(String message, HttpStatus httpStatus) {
        super(message);
        this.httpStatus = httpStatus;
    }
}
