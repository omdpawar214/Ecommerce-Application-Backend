package com.ecommerce.Ecommerce_App.service;

import com.ecommerce.Ecommerce_App.DTOs.ProductDTO;
import com.ecommerce.Ecommerce_App.DTOs.ProductResponse;
import com.ecommerce.Ecommerce_App.ExceptionHandler.ResourceNotFoundException;
import com.ecommerce.Ecommerce_App.Model.Category;
import com.ecommerce.Ecommerce_App.Model.Product;
import com.ecommerce.Ecommerce_App.repository.CategoryRepository;
import com.ecommerce.Ecommerce_App.repository.ProductRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService{
    private final ProductRepository productRepository;
    private final CategoryRepository CategoryRepository;
    private final  ModelMapper modelMapper;
    @Autowired
    public ProductServiceImpl(ProductRepository productRepository, CategoryRepository CategoryRepository, ModelMapper modelMapper) {
        this.productRepository = productRepository;
        this.CategoryRepository = CategoryRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public ProductDTO createProduct(Product product, Long categoryId) {
        //get the category and map relationship
        Category category = CategoryRepository.findById(categoryId).orElseThrow(
                ()->new ResourceNotFoundException("Category","category",categoryId));

        product.setCategory(category);
        product.setSpecialPrice(product.getPrice()- (product.getDiscount() *0.01) * product.getPrice());
        product.setImage("default.png");
        //save the new product and return its dto
        Product savedProduct  = productRepository.save(product);
        ProductDTO productDTO = modelMapper.map(savedProduct,ProductDTO.class);
        return productDTO;
    }

    @Override
    public ProductResponse gellAll() {
        //fetch all the products from repository
        List<Product> products = productRepository.findAll();
        //convert it to productDTO objects
        List<ProductDTO> productDTOS = new ArrayList<>();
        for(Product product : products){
            productDTOS.add(modelMapper.map(product,ProductDTO.class));
        }
        //return the response
        ProductResponse response = new ProductResponse();
        response.setContent(productDTOS);
        return response;
    }
}
