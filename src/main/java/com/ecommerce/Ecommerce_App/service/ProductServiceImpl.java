package com.ecommerce.Ecommerce_App.service;

import com.ecommerce.Ecommerce_App.DTOs.productDTOs.ProductDTO;
import com.ecommerce.Ecommerce_App.DTOs.productDTOs.ProductResponse;
import com.ecommerce.Ecommerce_App.ExceptionHandler.ApiException;
import com.ecommerce.Ecommerce_App.ExceptionHandler.ResourceNotFoundException;
import com.ecommerce.Ecommerce_App.Model.Category;
import com.ecommerce.Ecommerce_App.Model.Product;
import com.ecommerce.Ecommerce_App.repository.CategoryRepository;
import com.ecommerce.Ecommerce_App.repository.ProductRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService{
    @Autowired
    private FileService fileService;
    private final ProductRepository productRepository;
    private final CategoryRepository CategoryRepository;
    private final  ModelMapper modelMapper;
    @Autowired
    public ProductServiceImpl(ProductRepository productRepository, CategoryRepository CategoryRepository, ModelMapper modelMapper) {
        this.productRepository = productRepository;
        this.CategoryRepository = CategoryRepository;
        this.modelMapper = modelMapper;
    }
    @Value("${project.image}")
    private String path;

    @Override
    public ProductDTO createProduct( ProductDTO productDTO, Long categoryId) {
        //validating category creation
        String productName =productDTO.getProductName();
        Optional<Product> existingProduct = productRepository.findByProductName(productName);
        if(existingProduct.isPresent()){
            throw new ApiException("Cannot create this Product !! Product Already Exists");
        }

        Product product = modelMapper.map(productDTO,Product.class);
        //get the category and map relationship
        Category category = CategoryRepository.findById(categoryId).orElseThrow(
                ()->new ResourceNotFoundException("Category","category",categoryId));

        product.setCategory(category);
        product.setSpecialPrice(product.getPrice()- (product.getDiscount() *0.01) * product.getPrice());
        product.setImage("default.png");
        //save the new product and return its dto
        Product savedProduct  = productRepository.save(product);
        ProductDTO savedProductDTO = modelMapper.map(savedProduct,ProductDTO.class);
        return savedProductDTO;
    }

    @Override
    public ProductResponse gellAll(Integer pageNumber, Integer pageSize, String sortBy, String sortOrder) {
        //pagination and sorting logic
        Sort sortByAndOrder = sortOrder.equalsIgnoreCase("asc")
                ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();
        Pageable pageDetails = PageRequest.of(pageNumber,pageSize,sortByAndOrder);
        Page<Product> ProductPage = productRepository.findAll(pageDetails);
        List<Product> products = ProductPage.getContent();

            //validation
            if(products.isEmpty()) throw new ApiException("Response Can't Generate !! List is Empty");
        //convert it to productDTO objects
        List<ProductDTO> productDTOS = new ArrayList<>();
        for(Product product : products){
            productDTOS.add(modelMapper.map(product,ProductDTO.class));
        }
        //return the response with pagination details
        ProductResponse response = new ProductResponse();
        response.setContent(productDTOS);
        response.setPageNumber(ProductPage.getNumber());
        response.setPageSize(ProductPage.getSize());
        response.setTotalPages(ProductPage.getTotalPages());
        response.setTotalElements(ProductPage.getTotalElements());
        response.setLastPage(ProductPage.isLast());

        return response;
    }

    @Override
    public ProductResponse getProductsByCategoryName(String name, Integer pageNumber, Integer pageSize, String sortBy, String sortOrder) {
        //pagination and sorting logic
        Sort sortByAndOrder = sortOrder.equalsIgnoreCase("asc")
                ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();
        Pageable pageDetails = PageRequest.of(pageNumber,pageSize,sortByAndOrder);
        Page<Product> ProductPage = productRepository.findAll(pageDetails);
        List<Product> products = ProductPage.getContent();
                 //validation
                 if(products.isEmpty()) throw new ApiException("Response Can't Generate !! List is Empty");

        //convert it to productDTO objects
        List<ProductDTO> productDTOS = new ArrayList<>();
        for(Product product : products){
            productDTOS.add(modelMapper.map(product,ProductDTO.class));
        }
        //return the response
        ProductResponse response = new ProductResponse();
        response.setContent(productDTOS);
        response.setPageNumber(ProductPage.getNumber());
        response.setPageSize(ProductPage.getSize());
        response.setTotalPages(ProductPage.getTotalPages());
        response.setTotalElements(ProductPage.getTotalElements());
        response.setLastPage(ProductPage.isLast());
        return response;
    }

    @Override
    public ProductResponse findBykeyword(String keyword, Integer pageNumber, Integer pageSize, String sortBy, String sortOrder) {
        //pagination and sorting logic
        Sort sortByAndOrder = sortOrder.equalsIgnoreCase("asc")
                ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();
        Pageable pageDetails = PageRequest.of(pageNumber,pageSize,sortByAndOrder);
        Page<Product> ProductPage = productRepository.findByProductNameLikeIgnoreCase('%'+keyword+'%' , pageDetails);
        List<Product> products = ProductPage.getContent();
             //validation
             if(products.isEmpty()) throw new ApiException("Response Can't Generate !! List is Empty");

        //convert it to productDTO objects
        List<ProductDTO> productDTOS = new ArrayList<>();
        for(Product product : products){
            productDTOS.add(modelMapper.map(product,ProductDTO.class));
        }
        //return the response
        ProductResponse response = new ProductResponse();
        response.setContent(productDTOS);
        response.setContent(productDTOS);
        response.setPageNumber(ProductPage.getNumber());
        response.setPageSize(ProductPage.getSize());
        response.setTotalPages(ProductPage.getTotalPages());
        response.setTotalElements(ProductPage.getTotalElements());
        response.setLastPage(ProductPage.isLast());
        return response;
    }

    @Override
    public ProductDTO updateProduct(Long productId, ProductDTO productDTO) {
        Product product = modelMapper.map(productDTO,Product.class);
        //fetch product from the repository
        Product existingProduct  = productRepository.findById(productId).orElseThrow(()->new ResourceNotFoundException("Product","ProductId",productId));
        //change the new product with existing product
        product.setProductId(existingProduct.getProductId());
        product.setCategory(existingProduct.getCategory());
        product.setImage(existingProduct.getImage());
        product.setSpecialPrice(product.getPrice()- (product.getDiscount() *0.01) * product.getPrice());
        //save new product to repository
        Product savedProduct = productRepository.save(product);
        //convert product to product dto
        ProductDTO UpdatedProductDTO= modelMapper.map(savedProduct,ProductDTO.class);
        //return the dto
        return UpdatedProductDTO;
    }

    @Override
    public ProductDTO deleteProduct(Long productId) {
        //fetch teh product
        Product product = productRepository.findById(productId).orElseThrow(()->new ResourceNotFoundException("Product","productId",productId));
        //detach teh connection
        product.setCategory(null);
        //remove from repository
        productRepository.delete(product);

        return modelMapper.map(product,ProductDTO.class);
    }

    @Override
    public ProductDTO updateImage(Long productId, MultipartFile image) throws IOException {
        //fetch product from db
        Product product = productRepository.findById(productId).orElseThrow(
                ()->new ResourceNotFoundException("Product","productId",productId)
        );
         //upload image form server
        //get the file name
        String FileName = fileService.uploadImage(path,image);
        //get the new file name to the product
        product.setImage(FileName);
        //return the dto in appropriate format
        Product updatedProduct = productRepository.save(product);

        return modelMapper.map(updatedProduct,ProductDTO.class);
    }

}
