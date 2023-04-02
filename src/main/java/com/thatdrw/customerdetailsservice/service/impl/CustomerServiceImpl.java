package com.thatdrw.customerdetailsservice.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thatdrw.customerdetailsservice.entity.Address;
import com.thatdrw.customerdetailsservice.entity.Customer;
import com.thatdrw.customerdetailsservice.exception.EntityNotFoundException;
import com.thatdrw.customerdetailsservice.repository.CustomerRepository;
import com.thatdrw.customerdetailsservice.service.CustomerService;

import lombok.AllArgsConstructor;


@AllArgsConstructor
@Service
public class CustomerServiceImpl implements CustomerService {

    private CustomerRepository customerRepository;

    @Override
    @Transactional
    public Customer saveCustomer(Customer customer) {
        return customerRepository.save(customer);
    }

    @Override
    @Transactional
    public List<Customer> getCustomers() {
        return (List<Customer>)customerRepository.findAll();
    }

    @Override
    @Transactional
    public Customer getCustomer(Long id) {
        Optional<Customer> customer = customerRepository.findById(id);
        return unwrapCustomer(customer, id);
    }

    @Override
    @Transactional
    public List<Customer> findCustomer(String query) {
        Optional<List<Customer>> customer = customerRepository.findByFirstNameContainsOrLastNameContains(query, query);
        return unwrapCustomerList(customer, -1L);
    }

    @Override
    @Transactional
    public Customer updateCustomerAddress(Long id, Address address) {
        Customer customer = getCustomer(id);
        customer.setAddress(address);
        return saveCustomer(customer);
    }

    public static Customer unwrapCustomer(Optional<Customer> entity, Long id) {
        if (entity.isPresent())
            return entity.get();
        else
            throw new EntityNotFoundException(id, Customer.class);
    }

    public static List<Customer> unwrapCustomerList(Optional<List<Customer>> entity, Long id) {
        if (entity.isPresent())
            return entity.get();
        else
            throw new EntityNotFoundException(id, Customer.class);
    }
    
}
