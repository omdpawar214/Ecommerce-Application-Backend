package com.ecommerce.Ecommerce_App.DTOs.OrderDTOs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentDTO {
    private Long paymentId;
    private String pgName;
    private String pgStatus;
    private String pgPaymentId;
    private String paymentMethod;
    private String pgResponseMethod;
}

