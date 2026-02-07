package com.ecommerce.Ecommerce_App.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long addressId;

    @NotBlank
    @Size(min = 5 , message = "address street must be at-least 5 characters")
    private String street;

    @NotBlank
    @Size(min = 5 , message = "Building name must be at-least 5 characters")
    private String buildingName;
    @NotBlank
    @Size(min = 2 , message = "address city must be at-least 5 characters")
    private String city;
    @NotBlank
    @Size(min = 3 , message = "address state must be at-least 5 characters")
    private String state;
    @NotBlank
    @Size(min = 3 , message = "address  must be at-least 5 characters")
    private String country;
    @NotBlank
    @Size(min = 6 , message = "address pin-code must be at-least 5 characters")
    private String pinCode;

    @ToString.Exclude
    @ManyToMany(mappedBy = "Addresses")
    private List<User> user  = new ArrayList<>();


    public Address(String street, String buildingName, String city, String country, String state, String pinCode) {
        this.street = street;
        this.buildingName = buildingName;
        this.city = city;
        this.country = country;
        this.state = state;
        this.pinCode = pinCode;
    }
}
