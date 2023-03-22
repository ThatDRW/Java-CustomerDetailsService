package com.thatdrw.customerdetailsservice.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.thatdrw.customerdetailsservice.entity.Customer;

public interface CustomerRepository extends CrudRepository<Customer, Long> {
    Optional<Customer> findByFirstNameContainsOrLastNameContains(String firstName, String lastName);
    
}
