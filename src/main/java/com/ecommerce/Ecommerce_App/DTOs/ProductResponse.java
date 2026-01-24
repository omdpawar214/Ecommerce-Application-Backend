package com.ecommerce.Ecommerce_App.DTOs;

import com.ecommerce.Ecommerce_App.Model.Product;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductResponse {
    private List<ProductDTO> content;
}
