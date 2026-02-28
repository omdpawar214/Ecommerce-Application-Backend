package com.ecommerce.Ecommerce_App.service;

import com.ecommerce.Ecommerce_App.DTOs.CartDTOs.CartDTO;
import com.ecommerce.Ecommerce_App.DTOs.productDTOs.ProductDTO;
import com.ecommerce.Ecommerce_App.ExceptionHandler.ApiException;
import com.ecommerce.Ecommerce_App.ExceptionHandler.ResourceNotFoundException;
import com.ecommerce.Ecommerce_App.Model.Cart;
import com.ecommerce.Ecommerce_App.Model.CartItem;
import com.ecommerce.Ecommerce_App.Model.Product;
import com.ecommerce.Ecommerce_App.repository.CartItemRepository;
import com.ecommerce.Ecommerce_App.repository.CartRepository;
import com.ecommerce.Ecommerce_App.repository.ProductRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

@Service
public class CartServiceImpl implements CartService{

    //required dependencies
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private AuthUtils authUtils;
    @Autowired
    private CartItemRepository cartItemRepository;
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public CartDTO addProductToCart(Long productId, Integer quantity) {
        //find Existing cart or create one for current id
        Cart cart =  createCart();
        //get the product by product id
        Product product = productRepository.findById(productId).orElseThrow(()->
                new ResourceNotFoundException("Product","ProductId",productId));
        //perform basic validations
        CartItem cartItem = cartItemRepository.findCartItemByProductIdAndCartId(
                cart.getCart_Id(),productId
        );
        if(cartItem!= null){
            throw  new ApiException("Product already exists with the productId ->" + productId);
        }
        if(product.getQuantity()==0){
            throw  new ApiException(product.getProductName() + " is not available Currently");
        }
        if(product.getQuantity() < quantity){
            throw  new ApiException("required number of products are not available ");
        }
        //create Cart Item
        CartItem newCartItem = new CartItem();
        newCartItem.setProduct(product);
        newCartItem.setCart(cart);
        newCartItem.setQuantity(quantity);
        newCartItem.setDiscount(product.getDiscount());
        newCartItem.setProductPrice(product.getSpecialPrice());
        //save Cart Item and cart
        cartItemRepository.save(newCartItem);

        product.setQuantity(product.getQuantity());
        cart.setTotalPrice(cart.getTotalPrice()+(product.getSpecialPrice()*quantity));

        cartRepository.save(cart);

        //Creating the cartDTO object
        CartDTO cartDTO = modelMapper.map(cart,CartDTO.class);
        List<CartItem> cartItems = cart.getItems();
        Stream<ProductDTO> productDTOStream = cartItems.stream().map(item->{
            ProductDTO map = modelMapper.map(item.getProduct(),ProductDTO.class),
                    map.setQuantity(item.getQuantity());
            return map;
        });
        cartDTO.setProducts(productDTOStream.toList());
        //returning DTO object
        return cartDTO;
    }

    public Cart createCart (){
        Cart currCart = cartRepository.findCartByEmail(authUtils.loggedInEmail());
        if(currCart!= null){
            return currCart;
        }
        Cart newCart = new Cart();
        newCart.setTotalPrice(0.00);
        newCart.setUser(authUtils.loggedInUser());
        return cartRepository.save(newCart);
    }
}
