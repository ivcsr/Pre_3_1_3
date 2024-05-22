package com.kata.pre_3_1_2_sb.controller;

import com.kata.pre_3_1_2_sb.exception.NotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionController {
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<String> handleException(NotFoundException preException){
        return new ResponseEntity<>(preException.getMessage(), preException.getHttpStatus());
    }
}
