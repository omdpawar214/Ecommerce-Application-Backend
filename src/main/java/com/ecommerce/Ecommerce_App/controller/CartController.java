package com.ecommerce.Ecommerce_App.controller;

import com.ecommerce.Ecommerce_App.DTOs.CartDTOs.CartDTO;
import com.ecommerce.Ecommerce_App.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    //endpoint to fetch all the carts
    @GetMapping
    public ResponseEntity<List<CartDTO>> getAllCarts(){
        return new ResponseEntity<>(cartService.fetchAllCarts() , HttpStatus.OK);
    }

    //endpoint to fetch cart of current user
    @GetMapping("/user")
    public ResponseEntity<CartDTO> getUsersCart(){
        return new ResponseEntity<>(cartService.fetchUsersCart(), HttpStatus.OK);
    }
}
