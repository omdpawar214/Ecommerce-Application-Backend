package com.ecommerce.Ecommerce_App.repository;

import com.ecommerce.Ecommerce_App.Model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product , Long> {
    List<Product> findByCategoryName(String name);

    Page<Product> findByProductNameLikeIgnoreCase(String keyword, Pageable pageDetails);

    Optional<Product> findByProductName(String productName);

    Page<Product> findByCategory_Name(String name, Pageable pageDetails);
}
