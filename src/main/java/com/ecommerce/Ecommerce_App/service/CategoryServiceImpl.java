package com.ecommerce.Ecommerce_App.service;

import com.ecommerce.Ecommerce_App.Model.Category;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService{

    //dummy list to initially store teh categories
    List<Category> categories = new ArrayList<>();

    @Override
    public List<Category> getAllCategories() {
        return categories;
    }

    @Override
    public String saveCategory(Category category) {
        categories.add(category);
        return "Category Added Successfully !!";
    }
}
