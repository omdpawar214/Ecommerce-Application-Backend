package com.ecommerce.Ecommerce_App.service;

import com.ecommerce.Ecommerce_App.DTOs.AddressDTO;
import com.ecommerce.Ecommerce_App.DTOs.MessageResponse;
import com.ecommerce.Ecommerce_App.ExceptionHandler.ApiException;
import com.ecommerce.Ecommerce_App.ExceptionHandler.ResourceNotFoundException;
import com.ecommerce.Ecommerce_App.Model.Address;
import com.ecommerce.Ecommerce_App.Model.User;
import com.ecommerce.Ecommerce_App.Utility.AuthUtils;
import com.ecommerce.Ecommerce_App.repository.AddressRepository;
import com.ecommerce.Ecommerce_App.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AddressServiceImpl implements AddressService{
    //Required dependencies
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private AddressRepository addressRepository;
    @Autowired
    private AuthUtils authUtils;
    @Autowired
    private UserRepository userRepository;

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

    @Override
    public List<AddressDTO> getAllAddresses() {
        //get all the address
        List<Address> addresses = addressRepository.findAll();
        //convert all the addresses to addressDTOs
        List<AddressDTO> addressDTOS = new ArrayList<>();
        for (Address address: addresses){
            addressDTOS.add(modelMapper.map(address,AddressDTO.class));
        }
        //return this list
        return addressDTOS;
    }

    @Override
    public AddressDTO getAddressById(Long addressId) {
        Address address = addressRepository.findById(addressId).orElseThrow(()->
                new ResourceNotFoundException("Address","AddressID",addressId));
        return modelMapper.map(address,AddressDTO.class);
    }

    @Override
    public List<AddressDTO> getAllUsersAddresses() {
        User user = authUtils.loggedInUser();
        //fetch all teh user Addresses based on user Id
        List<Address> addresses = user.getAddresses();
        if(addresses.isEmpty()){
            throw new ApiException("No Address is Associated With this user");
        }
        //convert all the addresses to addressDTOs
        List<AddressDTO> addressDTOS = new ArrayList<>();
        for (Address address: addresses){
            addressDTOS.add(modelMapper.map(address,AddressDTO.class));
        }
        //return this list
        return addressDTOS;
    }

    @Override
    public AddressDTO updateAddress(AddressDTO addressDTO, Long addressId) {
        //get address by id
        Address currAddress = addressRepository.findById(addressId).orElseThrow(()->
                new ResourceNotFoundException("Address","AddressID",addressId));
        //update the required fields
        currAddress.setCity(addressDTO.getCity());
        currAddress.setState(addressDTO.getStreet());
        currAddress.setState(addressDTO.getState());
        currAddress.setCountry(addressDTO.getCountry());
        currAddress.setBuildingName(addressDTO.getBuildingName());
        currAddress.setPinCode(addressDTO.getPinCode());


        //save the address again
        Address savedAddress = addressRepository.save(currAddress);
        User user = authUtils.loggedInUser();
        user.getAddresses().removeIf(address -> address.getAddressId().equals(addressId));
        user.getAddresses().add(savedAddress);

        userRepository.save(user);

        return modelMapper.map(savedAddress,AddressDTO.class);
    }

    @Override
    public Object deleteAddressById(Long addressId) {
        //fetch the address
        Address address = addressRepository.findById(addressId).orElseThrow(()->
                new ResourceNotFoundException("Address","AddressID",addressId) );
        //remove the associations
        User user = authUtils.loggedInUser();
        user.getAddresses().removeIf(address1 -> address1.getAddressId().equals(addressId));
        address.setUser(null);
        userRepository.save(user);

        addressRepository.delete(address);
        //return message
        return new MessageResponse("the address with id -"+addressId+" has been removed successfully");
    }
}
