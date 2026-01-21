package com.ecommerce.Ecommerce_App.service;

import com.ecommerce.Ecommerce_App.DTOs.CategoryDTO;
import com.ecommerce.Ecommerce_App.DTOs.CategoryResponse;
import com.ecommerce.Ecommerce_App.Model.Category;

import java.util.List;

public interface CategoryService {

     CategoryResponse getAllCategories();
     String saveCategory(Category category);
     String deleteCategory(long id);
     CategoryResponse updateCategory(Category category, long categoryId);
}
