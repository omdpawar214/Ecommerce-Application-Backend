package com.ecommerce.Ecommerce_App.service;

import com.ecommerce.Ecommerce_App.DTOs.OrderDTOs.OrderDTO;
import com.ecommerce.Ecommerce_App.DTOs.OrderDTOs.OrderItemDTO;
import com.ecommerce.Ecommerce_App.DTOs.productDTOs.ProductDTO;
import com.ecommerce.Ecommerce_App.ExceptionHandler.ApiException;
import com.ecommerce.Ecommerce_App.ExceptionHandler.ResourceNotFoundException;
import com.ecommerce.Ecommerce_App.Model.*;
import com.ecommerce.Ecommerce_App.Utility.AuthUtils;
import com.ecommerce.Ecommerce_App.repository.*;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService{
    @Autowired
    private AuthUtils authUtils;
    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private AddressRepository addressRepository;
    @Autowired
    private PaymentRepository paymentRepository;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private OrderItemRepository orderItemRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private CartService cartService;
    @Autowired
    private ModelMapper modelMapper;


    //method to place the order
    @Override
    @Transactional
    public OrderDTO placeOrder(String paymentMethod, Long addressId, String pgName, String pgStatus, String pgPaymentId, String pgResponseMessage) {
        //get the current users email
        String email= authUtils.loggedInEmail();
        //get the users cart
        Cart cart  = cartRepository.findCartByEmail(email);
        if(cart==null){
            throw new ApiException("Cart does not exist");
        }
        Address address= addressRepository.findById(addressId).orElseThrow(()->
                new ResourceNotFoundException("Address","AddressId",addressId));
        //create a new order with payment info
        Order order = new Order();
        order.setEmail(email);
        order.setOrderDate(LocalDate.now());
        order.setTotalAmount(cart.getTotalPrice());
        order.setOrderStatus("Order Accepted !!");
        order.setOrderAddress(address);

        Payment payment = new Payment(paymentMethod,pgPaymentId,pgStatus,pgName,pgResponseMessage);
        payment.setOrder(order);
        payment = paymentRepository.save(payment);
        order.setPayment(payment);
        Order savedOrder = orderRepository.save(order);
        //get all the Items from the cart in orderItems
        List<CartItem> cartItems = cart.getItems();
        if(cartItems.isEmpty()){
            throw new ApiException("Cart is Empty");
        }
        List<OrderItem> orderItems =new ArrayList<>();
        for(CartItem cartItem :cartItems){
            OrderItem orderItem= new OrderItem();
            orderItem.setProduct(cartItem.getProduct());
            orderItem.setQuantity(cartItem.getQuantity());
            orderItem.setDiscount(cartItem.getDiscount());
            orderItem.setTotalPrice(cartItem.getProductPrice()*cartItem.getQuantity());
            orderItem.setOrder(savedOrder);
            orderItems.add(orderItem);
        }
        orderItems = orderItemRepository.saveAll(orderItems);
        savedOrder.setOrderItems(orderItems);
        orderRepository.save(savedOrder);

        //update product stock in Product in application
        List<Long> productIds = new ArrayList<>();
        for(CartItem cartItem:cartItems){
            //get the product
            Product product = cartItem.getProduct();
            //update the quantity of the product
            product.setQuantity(product.getQuantity()-cartItem.getQuantity());
            productRepository.save(product);
            productIds.add(product.getProductId());
        }
       //remove the cart Items from cart by product id
        for (Long productId:productIds){
            cartService.deleteProductFromTheCart(productId);
        }

        //send back the order summery
        OrderDTO orderDTO = modelMapper.map(savedOrder,OrderDTO.class);
        List<OrderItemDTO> orderItemDTOS = new ArrayList<>();
        for(OrderItem orderItem: orderItems){
            orderItemDTOS.add(modelMapper.map(orderItem, OrderItemDTO.class));
        }
        orderDTO.setOrderItems(orderItemDTOS);
        orderDTO.setAddressId(addressId);
        return orderDTO;
    }
}
