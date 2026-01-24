package com.ecommerce.Ecommerce_App.DTOs;

import jakarta.validation.constraints.NotNull;
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
    private double price;
    private double specialPrice;
    private Integer quantity;
    private Double discount;
    private String image;
}
