package com.thatdrw.customerdetailsservice.service;

import java.util.List;
import com.thatdrw.customerdetailsservice.entity.Customer;

/*  - [ ] Add a new customer
    - [ ] Retrieve all customers
    - [ ] Retrieve one customer by it's identifier
    - [ ] Search customers by first name and/or last name
    - [ ] Update the living address                         */


public interface CustomerService {
    Customer saveCustomer(Customer customer);

    List<Customer> getCustomers();
    
    Customer getCustomer(Long id);
    
    List<Customer> findCustomer(String query);

    void updateCustomerAddress(Long id, String address);
}
