package com.ecommerce.Ecommerce_App.controller;

import com.ecommerce.Ecommerce_App.Model.Category;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class CategoryController {

    // method to fetch all categories
    @GetMapping("/public/categories")
    public List<Category> getAllCategories(){
        return CategoryService.getAllCategories();
    }
}
