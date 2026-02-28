package com.ecommerce.Ecommerce_App.repository;

import com.ecommerce.Ecommerce_App.DTOs.CartDTOs.CartDTO;
import com.ecommerce.Ecommerce_App.Model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CartRepository extends JpaRepository<Cart,Long> {
    @Query("select c from cart c where c.user.email = ?1")
    Cart findCartByEmail(String email);
}
