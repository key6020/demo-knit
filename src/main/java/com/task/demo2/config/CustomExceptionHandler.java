package com.task.demo2.config;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.naming.AuthenticationException;

@Slf4j
@RestControllerAdvice
public class CustomExceptionHandler {
    @ExceptionHandler({AuthenticationException.class})
    public ResponseEntity<?> exceptionHandler(AuthenticationException ex) {
        ex.printStackTrace();
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized.");
    }

    @ExceptionHandler(value = {MalformedJwtException.class})
    protected ResponseEntity<Object> handleGenericExceptions(MalformedJwtException ex) {
        log.info(ex.getMessage());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ex.getMessage());
    }

    @ExceptionHandler(value = {IllegalArgumentException.class})
    protected ResponseEntity<Object> handleGenericExceptions(IllegalArgumentException ex) {
        log.info(ex.getMessage());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ex.getMessage());
    }

    @ExceptionHandler(value = {ExpiredJwtException.class})
    protected ResponseEntity<Object> handleGenericExceptions(ExpiredJwtException ex) {
        log.info(ex.getMessage());
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(ex.getMessage());
    }
}

