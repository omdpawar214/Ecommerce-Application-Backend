package com.ecommerce.Ecommerce_App.controller;

import com.ecommerce.Ecommerce_App.DTOs.AddressDTO;
import com.ecommerce.Ecommerce_App.service.AddressService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    //endpoint to fetch all the addresses
    @GetMapping("/addresses")
    public ResponseEntity<List<AddressDTO>> getAllAddresses(){
        return new ResponseEntity<>(addressService.getAllAddresses(),HttpStatus.OK);
    }

    //endpoint to get address by addressId
    @GetMapping("/addresses/{addressId}")
    public ResponseEntity<AddressDTO> getAddressById(@PathVariable Long addressId){
        return new ResponseEntity<>(addressService.getAddressById(addressId),HttpStatus.OK);
    }

    //endpoint to get Users Address
    @GetMapping("/user/addresses")
    public ResponseEntity<List<AddressDTO>> getAllUsersAddresses(){
        return new ResponseEntity<>(addressService.getAllUsersAddresses(),HttpStatus.OK);
    }

    //endpoint to update Address
    @PutMapping("/addresses/{addressId}")
    public ResponseEntity<AddressDTO> updateAddressById(@PathVariable Long addressId,
                                                         @RequestBody AddressDTO addressDTO){
        return new ResponseEntity<>(addressService.updateAddress(addressDTO,addressId),HttpStatus.OK);
    }
}
