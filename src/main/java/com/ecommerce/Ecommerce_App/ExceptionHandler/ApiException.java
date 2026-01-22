package com.ecommerce.Ecommerce_App.ExceptionHandler;

public class ApiException extends RuntimeException {
    public ApiException(String message)
    {
        super(message);
    }
}
