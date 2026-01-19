package com.ecommerce.Ecommerce_App.ExceptionHandler;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler{

    @ExceptionHandler
    public ResponseEntity<String> errorResponse(MethodArgumentNotValidException methodArgumentNotValidException){
        return new ResponseEntity<>("The request is not valid",HttpStatus.BAD_REQUEST);
    }
}
