package com.ecommerce.Ecommerce_App.controller;

import com.ecommerce.Ecommerce_App.Model.Category;
import com.ecommerce.Ecommerce_App.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")
public class CategoryController {

    //injecting object of category service
    private final CategoryService categoryService;
    @Autowired
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    // method to fetch all categories
    @GetMapping("/public/categories")
    public List<Category> getAllCategories(){
        return categoryService.getAllCategories();
    }

    //method to store teh category
    @PostMapping("/admin/category")
    public String addCategory(@RequestBody Category category){
        return categoryService.saveCategory(category);
    }

    //method to delete category by id
    @DeleteMapping("/admin/categories/{Id}")
    public String deleteCategory(@PathVariable int Id){
        categoryService.deleteCategory(Id);
        return "Category with Id :"+ Id + " is deleted successfully!!";
    }

}
