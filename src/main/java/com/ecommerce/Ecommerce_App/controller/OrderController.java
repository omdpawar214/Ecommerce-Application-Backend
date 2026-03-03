package com.ecommerce.Ecommerce_App.controller;

import com.ecommerce.Ecommerce_App.DTOs.OrderDTOs.OrderDTO;
import com.ecommerce.Ecommerce_App.DTOs.OrderDTOs.OrderRequestDTO;
import com.ecommerce.Ecommerce_App.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class OrderController {

    @Autowired
    private OrderService orderService;

    //endpoint to place the order
    @PostMapping("/order/users/payments/{paymentMethod}")
    public ResponseEntity<OrderDTO> orderPlacements(@PathVariable String paymentMethod,
                                                    @RequestBody OrderRequestDTO orderRequest){
        OrderDTO order =orderService.placeOrder(paymentMethod,
                orderRequest.getAddressId(),
                orderRequest.getPgName(),
                orderRequest.getPgStatus(),
                orderRequest.getPgPaymentId(),
                orderRequest.getPgResponseMessage()
        )
        return new ResponseEntity<>(order, HttpStatus.OK);
    }
}
