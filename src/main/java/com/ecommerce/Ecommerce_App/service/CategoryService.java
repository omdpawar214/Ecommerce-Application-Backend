package com.ecommerce.Ecommerce_App.service;

import com.ecommerce.Ecommerce_App.Model.Category;

import java.util.List;

public interface CategoryService {

     List<Category> getAllCategories();
     String saveCategory(Category category);
}
