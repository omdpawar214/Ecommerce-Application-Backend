package com.ecommerce.Ecommerce_App.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
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
    @NotEmpty(message = "This field cannot be blank")
    @Size(min = 2 , message = "this field at-least have 2 characters")
    private String productName;
    @NotEmpty(message = "This field cannot be blank")
    @Size(min = 5 , message = "this field at-least have 5 characters")
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
