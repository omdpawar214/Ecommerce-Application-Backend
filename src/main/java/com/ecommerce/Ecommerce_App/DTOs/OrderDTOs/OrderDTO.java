package com.ecommerce.Ecommerce_App.DTOs.OrderDTOs;

import com.ecommerce.Ecommerce_App.Model.Address;
import com.ecommerce.Ecommerce_App.Model.OrderItem;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.scheduling.support.SimpleTriggerContext;

import java.time.LocalDate;
import java.util.List;
import java.util.Locale;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDTO {
    private Long orderId;
    private String email;
    private List<OrderItemDTO> orderItems;
    private LocalDate orderDate;
    private PaymentDTO payment;
    private String orderStatus;
    private Double totalAmount;
    private Long addressId;
}
