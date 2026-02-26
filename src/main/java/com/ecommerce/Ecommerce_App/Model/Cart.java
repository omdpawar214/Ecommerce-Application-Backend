package com.ecommerce.Ecommerce_App.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cart_Id;

    private Double totalPrice =0.0;

    @OneToOne
    @JoinColumn(name = "UserId")
    private User user;

    @OneToMany(mappedBy = "cart" , cascade = {CascadeType.MERGE,CascadeType.PERSIST,CascadeType.REMOVE},orphanRemoval = true)
    private List<CartItem> items = new ArrayList<>();

}
