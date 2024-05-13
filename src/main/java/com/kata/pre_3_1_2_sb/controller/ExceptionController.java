package com.kata.pre_3_1_2_sb.controller;

import com.kata.pre_3_1_2_sb.exception.Pre312Exception;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionController {
    @ExceptionHandler(Pre312Exception.class)
    public ResponseEntity<String> handleException(Pre312Exception pre312Exception){
        return new ResponseEntity<>(pre312Exception.getMessage(), pre312Exception.getHttpStatus());
    }
}
