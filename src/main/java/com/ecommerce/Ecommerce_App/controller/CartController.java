package com.ecommerce.Ecommerce_App.controller;

import com.ecommerce.Ecommerce_App.DTOs.CartDTOs.CartDTO;
import com.ecommerce.Ecommerce_App.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/carts")
public class CartController {

    //Injecting required dependencies
    @Autowired
    private CartService cartService;
    //endpoint to add the product to the Cart
    @PostMapping("/product/{productId}/quantity/{quantity}")
    public ResponseEntity<CartDTO> addProductToCart(@PathVariable Long productId,
                                                    @PathVariable Integer quantity){
        CartDTO cartDTO = cartService.addProductToCart(productId,quantity);
        return new ResponseEntity<>(cartDTO, HttpStatus.OK);
    }
}
