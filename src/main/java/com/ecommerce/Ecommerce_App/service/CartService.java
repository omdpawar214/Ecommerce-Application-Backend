package com.ecommerce.Ecommerce_App.service;

import com.ecommerce.Ecommerce_App.DTOs.CartDTOs.CartDTO;

public interface CartService {
    CartDTO addProductToCart(Long productId, Integer quantity);
}
