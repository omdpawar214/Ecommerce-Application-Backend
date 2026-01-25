package com.ecommerce.Ecommerce_App.controller;

import com.ecommerce.Ecommerce_App.DTOs.ProductDTO;
import com.ecommerce.Ecommerce_App.DTOs.ProductResponse;
import com.ecommerce.Ecommerce_App.Model.Product;
import com.ecommerce.Ecommerce_App.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

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
    public ResponseEntity<ProductDTO> createProduct( @Valid @RequestBody ProductDTO productDTO ,
                                                    @PathVariable Long categoryId){
        return new ResponseEntity<>(productService.createProduct(productDTO , categoryId), HttpStatus.CREATED);
    }

    //endpoint to get all the products
    @GetMapping("/public/products")
    public ResponseEntity<ProductResponse> getAllProducts (){
        return new ResponseEntity<>(productService.gellAll() , HttpStatus.OK);
    }

    //get products by category name
    @GetMapping("/public/productsByCategory")
    public ResponseEntity<ProductResponse> getProductsByCategoryName (@RequestParam(name = "name") String name){
        return new ResponseEntity<>(productService.getProductsByCategoryName(name) , HttpStatus.OK);
    }

    //get products by keyword
    @GetMapping("/public/products/keyword/{keyword}")
    public ResponseEntity<ProductResponse> getProductsByKeyword(@PathVariable String keyword){
        return new ResponseEntity<>(productService.findBykeyword(keyword), HttpStatus.OK);
    }

    //endpoint to update product
    @PutMapping("/products/{productId}")
    public ResponseEntity<ProductDTO> updateProduct(@Valid @PathVariable Long productId,
                                                    @RequestBody ProductDTO productDTO){
        return new ResponseEntity<>(productService.updateProduct(productId,  productDTO),HttpStatus.ACCEPTED);
    }

    //endpoint to delete product
    @DeleteMapping("/admin/products/{productId}")
    public ResponseEntity<ProductDTO> deleteProduct(@PathVariable Long productId){
        return new ResponseEntity<>(productService.deleteProduct(productId),HttpStatus.OK);
    }

    //endpoint to set image to the product
    @PutMapping("/admin/products/{productId}/image")
    public ResponseEntity<ProductDTO> updateImage(@PathVariable Long productId,
                    @RequestParam("Image")MultipartFile image) throws IOException {
    return new ResponseEntity<>(productService.updateImage(productId,image),HttpStatus.ACCEPTED);
    }
}
