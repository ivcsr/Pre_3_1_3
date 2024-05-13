package com.kata.pre_3_1_2_sb.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class NotFoundException extends Pre312Exception {

    public NotFoundException(String message) {
        super(message, HttpStatus.NOT_FOUND);
    }
}
