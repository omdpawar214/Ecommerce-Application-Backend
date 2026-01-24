package com.ecommerce.Ecommerce_App.controller;

import com.ecommerce.Ecommerce_App.DTOs.ProductDTO;
import com.ecommerce.Ecommerce_App.Model.Product;
import com.ecommerce.Ecommerce_App.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class ProductController {

    //injecting required dependencies
    private final ProductService productService;
    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    //endpoint to create product
    @PostMapping("/admin/category/{categoryId}/product")
    public ResponseEntity<ProductDTO> createProduct( @Valid @RequestBody Product product ,
                                                    @PathVariable Long categoryId){
        return new ResponseEntity<>(productService.createProduct(product , categoryId), HttpStatus.CREATED);
    }
}
