package com.ecommerce.Ecommerce_App.controller;

import com.ecommerce.Ecommerce_App.Model.Category;
import com.ecommerce.Ecommerce_App.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

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
    public ResponseEntity<List<Category>>  getAllCategories(){
        return new ResponseEntity<>(categoryService.getAllCategories() , HttpStatus.OK);
    }

    //method to store teh category
    @PostMapping("/admin/category")
    public ResponseEntity<String > addCategory(@RequestBody Category category){
        String msg =categoryService.saveCategory(category);
        return new ResponseEntity<>(msg , HttpStatus.CREATED);
    }

    //method to delete category by id
    @DeleteMapping("/admin/categories/{Id}")
    public ResponseEntity<String > deleteCategory(@PathVariable long Id) {
        try {
            String msg = categoryService.deleteCategory(Id);
            return new ResponseEntity<>(msg , HttpStatus.ACCEPTED);
        }catch (ResponseStatusException e){
            return new ResponseEntity<>(e.getMessage(),e.getStatusCode());
        }
    }
}
