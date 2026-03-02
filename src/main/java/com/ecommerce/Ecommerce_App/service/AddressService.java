package com.ecommerce.Ecommerce_App.service;

import com.ecommerce.Ecommerce_App.DTOs.AddressDTO;
import jakarta.validation.Valid;

import java.util.List;


public interface AddressService {

    AddressDTO createAddress(@Valid AddressDTO addressDTO);

    List<AddressDTO> getAllAddresses();

    AddressDTO getAddressById(Long addressId);

    List<AddressDTO> getAllUsersAddresses();

    AddressDTO updateAddress(AddressDTO addressDTO, Long addressId);

    Object deleteAddressById(Long addressId);
}
