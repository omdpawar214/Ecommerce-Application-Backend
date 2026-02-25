package com.ecommerce.Ecommerce_App.DTOs.productDTOs;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductDTO {
    private Long productId;
    private String productName;
    private String productDescription;
    private Double price;
    private Double specialPrice;
    private Integer quantity;
    private Double discount;
    private String image;
}
