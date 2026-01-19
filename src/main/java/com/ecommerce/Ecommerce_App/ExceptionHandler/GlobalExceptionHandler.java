package com.ecommerce.Ecommerce_App.ExceptionHandler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler{

    @ExceptionHandler
    public ResponseEntity<String> errorResponse(MethodArgumentNotValidException e){
        String errorMessage = e.getBindingResult().getFieldError().getDefaultMessage();
        return new ResponseEntity<>(errorMessage,HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<String> anyExceptionResponse(ResourceNotFoundException e){
        String errorMessage = e.getMessage();
        return new ResponseEntity<>(errorMessage,HttpStatus.BAD_REQUEST);
    }}
