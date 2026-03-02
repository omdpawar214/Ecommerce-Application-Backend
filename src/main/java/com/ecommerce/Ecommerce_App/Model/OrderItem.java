 package com.ecommerce.Ecommerce_App.Model;

 import jakarta.persistence.*;
 import jakarta.validation.constraints.Email;
 import lombok.AllArgsConstructor;
 import lombok.Data;
 import lombok.NoArgsConstructor;

 @Entity
 @Data
 @Table(name = "order_items")
 @AllArgsConstructor
 @NoArgsConstructor
public class OrderItem {
     @Id
     @GeneratedValue(strategy = GenerationType.IDENTITY)
     private Long orderItemId;

     @ManyToOne
     @JoinColumn(name = "product_id")
     private Product product;

     private Double totalPrice;
     private Integer quantity;
     private Double discount;

     @ManyToOne
     @JoinColumn(name = "order_id")
     private Order order;
}
