package com.ecommerce.Ecommerce_App.ExceptionHandler;

public class ApiException extends RuntimeException {
    public ApiException()
    {
        super(" Category Creation Failed !!! The Category with this Name Already Exists !!");
    }
}
