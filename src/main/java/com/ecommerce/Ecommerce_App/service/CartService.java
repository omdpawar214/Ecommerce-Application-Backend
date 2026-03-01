package com.ecommerce.Ecommerce_App.service;

import com.ecommerce.Ecommerce_App.DTOs.CartDTOs.CartDTO;

import java.util.List;

public interface CartService {
    CartDTO addProductToCart(Long productId, Integer quantity);

    List<CartDTO> fetchAllCarts();

    CartDTO fetchUsersCart();

    CartDTO updateQuantity(Long productId, String operation);

    Object deleteProductFromTheCart(Long productId);
}
