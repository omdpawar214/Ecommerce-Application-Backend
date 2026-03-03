package com.ecommerce.Ecommerce_App.service;

import com.ecommerce.Ecommerce_App.DTOs.OrderDTOs.OrderDTO;

public interface OrderService {
    OrderDTO placeOrder(String paymentMethod, Long addressId, String pgName, String pgStatus, String pgPaymentId, String pgResponseMessage);
}
