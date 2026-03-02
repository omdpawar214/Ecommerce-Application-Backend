package com.ecommerce.Ecommerce_App.service;

import com.ecommerce.Ecommerce_App.DTOs.AddressDTO;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;

import java.util.List;


public interface AddressService {

    AddressDTO createAddress(@Valid AddressDTO addressDTO);

    List<AddressDTO> getAllAddresses();
}
