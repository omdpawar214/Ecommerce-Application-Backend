package com.ecommerce.Ecommerce_App.repository;

import com.ecommerce.Ecommerce_App.Model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category,Long> {

}
