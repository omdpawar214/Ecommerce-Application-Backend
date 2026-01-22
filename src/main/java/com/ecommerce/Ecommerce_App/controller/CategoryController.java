package com.ecommerce.Ecommerce_App.controller;

import com.ecommerce.Ecommerce_App.DTOs.CategoryDTO;
import com.ecommerce.Ecommerce_App.DTOs.CategoryResponse;
import com.ecommerce.Ecommerce_App.Model.Category;
import com.ecommerce.Ecommerce_App.service.CategoryService;
import jakarta.validation.Valid;
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
    public ResponseEntity<CategoryResponse>  getAllCategories(
            @RequestParam(name = "PageNumber") Integer PageNumber,
            @RequestParam(name = "PageSize") Integer PageSize
    ){
        return new ResponseEntity<>(categoryService.getAllCategories(PageNumber , PageSize) , HttpStatus.OK);
    }

    //method to store teh category
    @PostMapping("/admin/category")
    public ResponseEntity<CategoryDTO> addCategory( @Valid @RequestBody CategoryDTO categoryDTO){
        return new ResponseEntity<>( categoryService.saveCategory(categoryDTO) , HttpStatus.CREATED);
    }

    //method to delete category by id
    @DeleteMapping("/admin/categories/{Id}")
    public ResponseEntity<CategoryDTO > deleteCategory(@PathVariable long Id) {

            CategoryDTO deletedCategoryDTO = categoryService.deleteCategory(Id);
            return new ResponseEntity<>( deletedCategoryDTO, HttpStatus.ACCEPTED);
    }

    //method to update category
    @PutMapping("/admin/categories/{categoryId}")
    public ResponseEntity<CategoryDTO> updateCategory(@RequestBody CategoryDTO categoryDTO ,@PathVariable  long categoryId){
            CategoryDTO UpdatedCategoryDTO = categoryService.updateCategory(categoryDTO,categoryId);
            return new ResponseEntity<>(UpdatedCategoryDTO , HttpStatus.ACCEPTED);
    }
}
