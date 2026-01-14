package com.ecommerce.Ecommerce_App.service;

import com.ecommerce.Ecommerce_App.Model.Category;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService{

    //dummy list to initially store teh categories
    List<Category> categories = new ArrayList<>();

    //creating variable to track category id
    long IdNo = 0L;

    @Override
    public List<Category> getAllCategories() {
        return categories;
    }

    @Override
    public String saveCategory(Category category) {
        IdNo++;
        category.setCategoryId(IdNo);
        categories.add(category);
        return "Category Added Successfully !!";
    }

    @Override
    public String deleteCategory(long id) {
        Category category = null;
        if (id > IdNo || id <= 0) {
            return "Category not Found";
        }
            for (Category category1 : categories) {
                if (category1.getCategoryId() == id) {
                    category = category1;
                }
            }
            categories.remove(category);
            return "Category with Id :"+ id + " is deleted successfully!!";
    }
}
