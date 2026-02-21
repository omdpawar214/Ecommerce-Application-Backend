package com.ecommerce.Ecommerce_App.DTOs;

import lombok.Data;

@Data
public class MessageResponse {
    private String msg ;

    public MessageResponse(String msg) {
        this.msg = msg;
    }
}
