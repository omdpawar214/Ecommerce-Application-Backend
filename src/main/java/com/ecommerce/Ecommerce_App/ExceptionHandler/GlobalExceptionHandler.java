package com.ecommerce.Ecommerce_App.ExceptionHandler;

import com.ecommerce.Ecommerce_App.DTOs.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler{

    @ExceptionHandler
    public ResponseEntity<String> errorResponse(MethodArgumentNotValidException e){
        //String errorMessage = e.getBindingResult().getFieldError().getDefaultMessage();
        return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiResponse> anyExceptionResponse(ResourceNotFoundException e){
        String errorMessage = e.getMessage();
        ApiResponse apiResponse= new ApiResponse(errorMessage,false);
        return new ResponseEntity<>(apiResponse,HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ApiException.class)
    public ResponseEntity<ApiResponse> ApiExceptionResponse(ApiException e){
        String errorMessage = e.getMessage();
        ApiResponse apiResponse= new ApiResponse(errorMessage,false);
        return new ResponseEntity<>(apiResponse,HttpStatus.BAD_REQUEST);
    }

}
