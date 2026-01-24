package com.ecommerce.Ecommerce_App.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Long productId;
    @NotBlank
    private String productName;
    @NotBlank
    private String productDescription;
    @NotNull
    private double price;
    @NotNull
    private double specialPrice;
    @NotNull
    private Integer quantity;

    @ManyToOne
    @JoinColumn(name = "category_Id")
    private Category category;
}
