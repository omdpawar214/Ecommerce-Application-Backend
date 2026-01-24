package com.ecommerce.Ecommerce_App.service;

import com.ecommerce.Ecommerce_App.DTOs.ProductDTO;
import com.ecommerce.Ecommerce_App.Model.Product;

public interface ProductService {
    ProductDTO createProduct(Product product, Long categoryId);
}
