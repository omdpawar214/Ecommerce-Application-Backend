package com.ecommerce.Ecommerce_App.DTOs.CartDTOs;

import com.ecommerce.Ecommerce_App.DTOs.productDTOs.ProductDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartItemDTO {
    private Long cartItemId;
    private CartDTO cart;
    private ProductDTO productDTO;
    private Double discount;
    private Double productPrice;
    private Integer quantity;
}
