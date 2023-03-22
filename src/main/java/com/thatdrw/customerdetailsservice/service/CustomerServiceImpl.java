package com.thatdrw.customerdetailsservice.service;

import java.util.List;
import java.util.Optional;


import org.springframework.stereotype.Service;

import com.thatdrw.customerdetailsservice.entity.Customer;
import com.thatdrw.customerdetailsservice.exception.EntityNotFoundException;
import com.thatdrw.customerdetailsservice.repository.CustomerRepository;

import lombok.AllArgsConstructor;


@AllArgsConstructor
@Service
public class CustomerServiceImpl implements CustomerService {

    private CustomerRepository customerRepository;

    @Override
    public Customer saveCustomer(Customer customer) {
        return customerRepository.save(customer);
    }

    @Override
    public List<Customer> getCustomers() {
        return (List<Customer>)customerRepository.findAll();
    }

    @Override
    public Customer getCustomer(Long id) {
        Optional<Customer> customer = customerRepository.findById(id);
        return unwrapCustomer(customer, id);
    }

    @Override
    public List<Customer> findCustomer(String query) {
        Optional<List<Customer>> customer = customerRepository.findByFirstNameContainsOrLastNameContains(query, query);
        return unwrapCustomerList(customer, -1L);
    }

    @Override
    public void updateCustomerAddress(Long id, String address) {
        Customer customer = getCustomer(id);
        customer.setAddress(address);
        saveCustomer(customer);
    }

    static Customer unwrapCustomer(Optional<Customer> entity, Long id) {
        if (entity.isPresent())
            return entity.get();
        else
            throw new EntityNotFoundException(id, Customer.class);
    }

    static List<Customer> unwrapCustomerList(Optional<List<Customer>> entity, Long id) {
        if (entity.isPresent())
            return entity.get();
        else
            throw new EntityNotFoundException(id, Customer.class);
    }
    

    
}
