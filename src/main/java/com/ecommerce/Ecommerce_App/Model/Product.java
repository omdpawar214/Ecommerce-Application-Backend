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
    private String productName;
    private String productDescription;
    private Double price;
    private Double specialPrice;
    private Integer quantity;
    private Double discount;
    private String image;

    @ManyToOne
    @JoinColumn(name = "category_Id")
    private Category category;
}
