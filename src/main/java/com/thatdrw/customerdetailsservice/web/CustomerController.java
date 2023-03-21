package com.thatdrw.customerdetailsservice.web;

import javax.validation.Valid;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.thatdrw.customerdetailsservice.entity.Customer;
import com.thatdrw.customerdetailsservice.service.CustomerService;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping("/customer")
public class CustomerController {
    
    CustomerService customerService;

    @PostMapping
    public ResponseEntity<Customer> saveCustomer(@Valid @RequestBody Customer customer) {
        return new ResponseEntity<>(customerService.saveCustomer(customer), HttpStatus.CREATED);
    }
    
    @GetMapping("/all")
    public ResponseEntity<List<Customer>> getCustomers() {
        return new ResponseEntity<>(customerService.getCustomers(), HttpStatus.OK);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Customer> getCustomer(@PathVariable Long id) {
        return new ResponseEntity<>(customerService.getCustomer(id), HttpStatus.OK);
    }

    @GetMapping("/find/{query}")
    public ResponseEntity<Customer> findCustomer(@PathVariable String query) {
        return new ResponseEntity<>(customerService.findCustomer(query), HttpStatus.OK);
    }
    
    @PostMapping("/{id}/updateAddress")
    public ResponseEntity<Customer> updateAddress(@PathVariable Long id, @RequestBody String address) {
        Customer customer = customerService.getCustomer(id);
        customer.setAddress(address);
        return new ResponseEntity<>(customerService.saveCustomer(customer), HttpStatus.OK);
    }
}
