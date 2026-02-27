package com.ecommerce.Ecommerce_App.repository;

import com.ecommerce.Ecommerce_App.Model.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CarItemRepository extends JpaRepository<CartItem,Long> {
}
