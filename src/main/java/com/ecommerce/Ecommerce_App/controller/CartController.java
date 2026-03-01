package com.ecommerce.Ecommerce_App.controller;

import com.ecommerce.Ecommerce_App.DTOs.CartDTOs.CartDTO;
import com.ecommerce.Ecommerce_App.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
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
    @GetMapping("/users/cart")
    public ResponseEntity<CartDTO> getUsersCart(){
        return new ResponseEntity<>(cartService.fetchUsersCart(), HttpStatus.OK);
    }

    //endpoint to update the quantity of the products in cart
    @PutMapping("/products/{productId}/quantity/{operation}")
    public ResponseEntity<CartDTO> updateQuantity(@PathVariable Long productId,
                                                    @PathVariable String operation){
        return new ResponseEntity<>(cartService.updateQuantity(productId,operation), HttpStatus.OK);
    }

    //end point to delete an product from the cart
    @DeleteMapping("/products/{productId}")
    public ResponseEntity<?> deleteProductFromCart(@PathVariable Long productId){
        return new ResponseEntity<>(cartService.deleteProductFromTheCart(productId),HttpStatus.OK);
    }
}
