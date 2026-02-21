package com.ecommerce.Ecommerce_App.repository;

import com.ecommerce.Ecommerce_App.Model.User;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
    Optional<User> findByUserName(String username);
    Boolean existsByUserName(String userName);

    Boolean existsByEmail(@NotBlank @Size(min = 2 ,message = "this Field must Contains at-least 2 characters") String name);
}
