package com.ecommerce.Ecommerce_App.service;

import com.ecommerce.Ecommerce_App.DTOs.ProductDTO;
import com.ecommerce.Ecommerce_App.DTOs.ProductResponse;
import com.ecommerce.Ecommerce_App.Model.Product;

public interface ProductService {
    ProductDTO createProduct(@Valid ProductDTO product, Long categoryId);

    ProductResponse gellAll();

    ProductResponse getProductsByCategoryName(String name);

    ProductResponse findBykeyword(String keyword);

    ProductDTO updateProduct(Long productId, ProductDTO product);

    ProductDTO deleteProduct(Long productId);
}
