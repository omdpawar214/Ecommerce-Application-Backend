package com.ecommerce.Ecommerce_App.service;

import com.ecommerce.Ecommerce_App.DTOs.CategoryDTO;
import com.ecommerce.Ecommerce_App.DTOs.CategoryResponse;
import com.ecommerce.Ecommerce_App.Model.Category;

import java.util.List;

public interface CategoryService {

     CategoryResponse getAllCategories(Integer PageNumber , Integer PageSize ,String sortBy , String sortOrder);
     CategoryDTO saveCategory(CategoryDTO categoryDTO);
     CategoryDTO deleteCategory(long id);
     CategoryDTO  updateCategory(CategoryDTO categoryDTO, long categoryId);
}
