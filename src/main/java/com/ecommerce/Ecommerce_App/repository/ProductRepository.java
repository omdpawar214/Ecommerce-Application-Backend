package com.ecommerce.Ecommerce_App.repository;

import com.ecommerce.Ecommerce_App.Model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product , Long> {
    List<Product> findByCategoryName(String name);
}
