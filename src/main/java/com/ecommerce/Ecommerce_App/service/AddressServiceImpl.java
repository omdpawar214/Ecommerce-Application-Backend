package com.ecommerce.Ecommerce_App.service;

import com.ecommerce.Ecommerce_App.DTOs.AddressDTO;
import com.ecommerce.Ecommerce_App.Model.Address;
import com.ecommerce.Ecommerce_App.Model.User;
import com.ecommerce.Ecommerce_App.Utility.AuthUtils;
import com.ecommerce.Ecommerce_App.repository.AddressRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AddressServiceImpl implements AddressService{
    //Required dependencies
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private AddressRepository addressRepository;
    @Autowired
    private AuthUtils authUtils;

    @Override
    public AddressDTO createAddress(AddressDTO addressDTO) {
        //convert the Address dto class into Address class
        Address address  = modelMapper.map(addressDTO,Address.class);
        //assign this address to the current user
        User currUser = authUtils.loggedInUser();
        currUser.getAddresses().add(address);
        currUser.setAddresses(currUser.getAddresses());
        address.setUser(currUser);
        //save this address to repository
        Address savedAddress = addressRepository.save(address);
        //convert this saved address to addressDTO
        return modelMapper.map(savedAddress,AddressDTO.class);
    }
}
