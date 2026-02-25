package com.ecommerce.Ecommerce_App.service;

import com.ecommerce.Ecommerce_App.DTOs.categoryDTOs.CategoryDTO;
import com.ecommerce.Ecommerce_App.DTOs.categoryDTOs.CategoryResponse;

public interface CategoryService {

     CategoryResponse getAllCategories(Integer PageNumber , Integer PageSize ,String sortBy , String sortOrder);
     CategoryDTO saveCategory(CategoryDTO categoryDTO);
     CategoryDTO deleteCategory(long id);
     CategoryDTO  updateCategory(CategoryDTO categoryDTO, long categoryId);
}
