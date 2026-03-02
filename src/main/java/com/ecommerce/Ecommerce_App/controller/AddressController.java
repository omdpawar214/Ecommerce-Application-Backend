package com.ecommerce.Ecommerce_App.controller;

import com.ecommerce.Ecommerce_App.DTOs.AddressDTO;
import com.ecommerce.Ecommerce_App.service.AddressService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class AddressController {

    @Autowired
    private AddressService addressService;

    //Endpoint to create address and add the address to the user
    @PostMapping("/addresses")
    public ResponseEntity<AddressDTO> createAddress(@Valid @RequestBody AddressDTO addressDTO){
        return new ResponseEntity<>(addressService.createAddress(addressDTO), HttpStatus.CREATED);
    }


}
