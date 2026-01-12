package com.ecommerce.Ecommerce_App.controller;

import com.ecommerce.Ecommerce_App.Model.Category;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")
public class CategoryController {

    //dummy list to initially store teh categories
    List<Category> categories = new ArrayList<>();

    // method to fetch all categories
    @GetMapping("/public/categories")
    public List<Category> getAllCategories(){
        return categories;
    }

    //method to store teh category
    @PostMapping("/admin/category")
    public String addCategory(@RequestBody Category category){
        categories.add(category);
         return "Category Added Successfully !!";
    }

}
