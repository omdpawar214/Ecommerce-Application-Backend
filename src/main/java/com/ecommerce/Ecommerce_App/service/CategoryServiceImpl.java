package com.ecommerce.Ecommerce_App.service;

import com.ecommerce.Ecommerce_App.ExceptionHandler.ResourceNotFoundException;
import com.ecommerce.Ecommerce_App.Model.Category;
import com.ecommerce.Ecommerce_App.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceImpl implements CategoryService{

    //injecting repository layers dependency using field injection
    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    @Override
    public String saveCategory(Category category) {
        categoryRepository.save(category);
        return "Category Added Successfully !!";
    }

    @Override
    public String deleteCategory(long id) {
        // Method -1 to delete category
        Optional<Category> category = categoryRepository.findById(id);
        if(category.isPresent()){
            Category foundCategory = category.get();
            categoryRepository.delete(foundCategory);
            return "Category with Id :"+ id + " is deleted successfully!!";
        }else{
            throw new ResourceNotFoundException("category","categoryId",id);
        }

        // Method -2 to delete category
//          Category foundCategory = categoryRepository.findById(id).orElseThrow(()-> new ResponseStatusException(
//          HttpStatus.NOT_FOUND,"Category Not found with given id"
//        ));
//        categoryRepository.delete(foundCategory);

        //method -3 to delete category
         // categoryRepository.deleteById(id);
            //return "Category with Id :"+ id + " is deleted successfully!!";
    }

    @Override
    public String updateCategory(Category category , long categoryId) {
         //find the category
        Category foundCategory = categoryRepository.findById(categoryId).orElseThrow(()-> new ResourceNotFoundException(
                "category","categoryId",categoryId));
        //set required changes
        foundCategory.setName(category.getName());
        //save it to collection
        categoryRepository.save(foundCategory);
        return "Category Updated Successfully !!";
    }
}
