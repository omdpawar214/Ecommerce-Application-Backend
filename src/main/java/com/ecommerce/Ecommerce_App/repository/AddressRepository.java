package com.ecommerce.Ecommerce_App.repository;

import com.ecommerce.Ecommerce_App.Model.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AddressRepository extends JpaRepository<Address,Long> {
}
