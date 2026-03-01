package com.ecommerce.Ecommerce_App.service;

import com.ecommerce.Ecommerce_App.DTOs.CartDTOs.CartDTO;
import com.ecommerce.Ecommerce_App.DTOs.MessageResponse;
import com.ecommerce.Ecommerce_App.DTOs.productDTOs.ProductDTO;
import com.ecommerce.Ecommerce_App.ExceptionHandler.ApiException;
import com.ecommerce.Ecommerce_App.ExceptionHandler.ResourceNotFoundException;
import com.ecommerce.Ecommerce_App.Model.Cart;
import com.ecommerce.Ecommerce_App.Model.CartItem;
import com.ecommerce.Ecommerce_App.Model.Product;
import com.ecommerce.Ecommerce_App.Utility.AuthUtils;
import com.ecommerce.Ecommerce_App.repository.CartItemRepository;
import com.ecommerce.Ecommerce_App.repository.CartRepository;
import com.ecommerce.Ecommerce_App.repository.ProductRepository;
import jakarta.transaction.Transactional;
import org.aspectj.weaver.AjAttribute;
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
        cart.getItems().add(newCartItem);

        //Creating the cartDTO object
        CartDTO cartDTO = modelMapper.map(cart,CartDTO.class);
        List<CartItem> cartItems = cart.getItems();
        Stream<ProductDTO> productDTOStream = cartItems.stream().map(item->{
            ProductDTO map = modelMapper.map(item.getProduct(),ProductDTO.class);
            map.setQuantity(item.getQuantity());
            return map;
        });
        cartDTO.setProducts(productDTOStream.toList());
        //returning DTO object
        return cartDTO;
    }

    //method to fetch all the carts present
    @Override
    public List<CartDTO> fetchAllCarts() {
        List<Cart> carts= cartRepository.findAll();
        if (carts.isEmpty()){
            throw new ApiException("No cart Exist");
        }
        //we need to convert carts to cartDTOs with that we also have to convert products in the list of cartItems associated with cart to the list of productDTO for CartDTO object
        List<CartDTO> cartDTOS = new ArrayList<>();
        for(Cart cart : carts){
            CartDTO cartDTO = modelMapper.map(cart,CartDTO.class);
            List<ProductDTO> products = new ArrayList<>();
            List<CartItem> cartItems = cart.getItems();
            for (CartItem cartItem: cartItems){
                ProductDTO product= modelMapper.map(cartItem.getProduct(),ProductDTO.class);
                product.setQuantity(cartItem.getQuantity());
                products.add(product);
            }
            cartDTO.setProducts(products);
            cartDTOS.add(cartDTO);
        }
        return cartDTOS;
    }

    //method to fetch current users cart
    @Override
    public CartDTO fetchUsersCart() {
        //get the current users cart
        Cart cart = cartRepository.findCartByEmail(authUtils.loggedInEmail());
        if(cart == null){
            throw new ApiException("Current User Don't have any Cart");
        }
        //convert the cart object to the cartDTo object
        CartDTO cartDTO = modelMapper.map(cart,CartDTO.class);
        List<ProductDTO> products = new ArrayList<>();
        List<CartItem> cartItems = cart.getItems();
        for (CartItem cartItem: cartItems){
            ProductDTO product= modelMapper.map(cartItem.getProduct(),ProductDTO.class);
            product.setQuantity(cartItem.getQuantity());
            products.add(product);
        }
        cartDTO.setProducts(products);
        return cartDTO;
    }

    //method to update the quantity of the product inside the cart
    @Override
    @Transactional
    public CartDTO updateQuantity(Long productId, String operation) {

        //get the current user cart
        Cart cart = cartRepository.findCartByEmail(authUtils.loggedInEmail());
        if(cart==null){
            throw new ApiException("Cart does not exist");
        }
        //validate if product exist
        Product product =  productRepository.findById(productId).orElseThrow(()->
                new ResourceNotFoundException("product", "productId", productId));
        //get the cart item and update the quantity
        CartItem cartItem = cartItemRepository.findCartItemByProductIdAndCartId(cart.getCart_Id(),productId);
        if(cartItem==null){
            throw new ApiException("Product with id -"+productId+" does not exist in the users cart");
        }
        if(operation.equals("+") && cartItem.getQuantity()+1<=product.getQuantity()){
            cartItem.setQuantity(cartItem.getQuantity()+1);
            cartItem.setProductPrice(product.getSpecialPrice()* cartItem.getQuantity());
            cart.setTotalPrice(cart.getTotalPrice() + product.getSpecialPrice());
        }else if(operation.equals("-") && cartItem.getQuantity()-1>=0){
            cartItem.setQuantity(cartItem.getQuantity()-1);
            cartItem.setProductPrice(product.getSpecialPrice()* cartItem.getQuantity());
            cart.setTotalPrice(cart.getTotalPrice() - product.getSpecialPrice());
        }
        //save it to the repository
        CartItem UpdatedCartItem = cartItemRepository.save(cartItem);
        if(UpdatedCartItem.getQuantity()==0){
            cart.getItems().remove(cartItem);
            cartItemRepository.deleteById(cartItem.getCartItemId());
        }
        cartRepository.save(cart);
        //return cartDTO
        CartDTO cartDTO = modelMapper.map(cart,CartDTO.class);
        List<ProductDTO> products = new ArrayList<>();
        List<CartItem> cartItems = cart.getItems();
        for (CartItem currCartItem: cartItems){
            ProductDTO productDTO= modelMapper.map(currCartItem.getProduct(),ProductDTO.class);
            productDTO.setQuantity(currCartItem.getQuantity());
            products.add(productDTO);
        }
        cartDTO.setProducts(products);
        return cartDTO;

    }

    //method to delete the product from the cart;
    @Transactional
    @Override
    public Object deleteProductFromTheCart(Long productId) {
        //fetch the current users cart
        Cart cart = cartRepository.findCartByEmail(authUtils.loggedInEmail());
        if(cart==null){
            throw new ApiException("Cart does not exist");
        }
        //check whether the product Exist in the cart
        CartItem cartItem = cartItemRepository.findCartItemByProductIdAndCartId(cart.getCart_Id(),productId);
        if(cartItem==null){
            throw new ApiException("Product with id -"+productId+" does not exist in the users cart");
        }
        //remove the cartItem
        cart.setTotalPrice(cart.getTotalPrice()-cartItem.getProductPrice());
        cart.getItems().remove(cartItem);
        cartItem.setCart(null);
        cartItemRepository.deleteById(cartItem.getCartItemId());
        //save the cart
        cartRepository.save(cart);
        //return message response
        return new MessageResponse("the Product with ProductId-"+productId+" has removed from cart Successfully");
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
