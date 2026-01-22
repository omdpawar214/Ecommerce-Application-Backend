package com.ecommerce.Ecommerce_App.service;

import com.ecommerce.Ecommerce_App.DTOs.CategoryDTO;
import com.ecommerce.Ecommerce_App.DTOs.CategoryResponse;
import com.ecommerce.Ecommerce_App.ExceptionHandler.ApiException;
import com.ecommerce.Ecommerce_App.ExceptionHandler.ResourceNotFoundException;
import com.ecommerce.Ecommerce_App.Model.Category;
import com.ecommerce.Ecommerce_App.repository.CategoryRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public CategoryResponse getAllCategories(Integer PageNumber , Integer PageSize) {

        //get the Page ask by user using pagination concept
        Pageable pageDetails = PageRequest.of(PageNumber,PageSize);
        Page<Category> CategoryPage = categoryRepository.findAll(pageDetails);
        List<Category> categories = CategoryPage.getContent();
        if(categories.isEmpty()){
            throw new ApiException("List is Empty ! cannot Generate Response");
        }
        //converting Category list to category dto list
        List<CategoryDTO> categoryDTOSlist = new ArrayList<>();
        for(Category category : categories){
            categoryDTOSlist.add(modelMapper.map(category,CategoryDTO.class));
        }
        //creating Category Response
        CategoryResponse categoryResponse = new CategoryResponse();
        categoryResponse.setContent(categoryDTOSlist);

        return categoryResponse;
    }

    @Override
    public CategoryDTO saveCategory(CategoryDTO categoryDTO) {
        //checking for duplicates in repository
        String categoryName = categoryDTO.getName();
        Optional<Category> foundCategory = categoryRepository.findByName(categoryName);
        if(foundCategory.isPresent()){
            throw new ApiException("This Category has already been created!");
        }
        //converting category dto to category entity to make db exchange
        Category category = modelMapper.map(categoryDTO,Category.class);
        categoryRepository.save(category);
        categoryDTO.setCategoryId(category.getCategoryId());
        return categoryDTO;
    }

    @Override
    public CategoryDTO deleteCategory(long id) {

        Optional<Category> category = categoryRepository.findById(id);
        if(category.isPresent()){
            Category foundCategory = category.get();
            CategoryDTO deletedCategoryDto = modelMapper.map(foundCategory,CategoryDTO.class);
            categoryRepository.delete(foundCategory);
            return deletedCategoryDto;
        }else{
            throw new ResourceNotFoundException("category","categoryId",id);
        }

    }

    @Override
    public CategoryDTO updateCategory(CategoryDTO categoryDTO , long categoryId) {
         //find the category
        Category foundCategory = categoryRepository.findById(categoryId).orElseThrow(()-> new ResourceNotFoundException(
                "category","categoryId",categoryId));
        //set required changes
        foundCategory.setName(categoryDTO.getName());
        //save it to collection
         Category UpdatedCategory = categoryRepository.save(foundCategory);

        CategoryDTO updatedCategoryDTO = modelMapper.map(UpdatedCategory,CategoryDTO.class);
        return updatedCategoryDTO;
    }
}
