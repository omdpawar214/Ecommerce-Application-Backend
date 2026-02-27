package com.ecommerce.Ecommerce_App.repository;

import com.ecommerce.Ecommerce_App.Model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartRepository extends JpaRepository<Cart,Long> {
}
