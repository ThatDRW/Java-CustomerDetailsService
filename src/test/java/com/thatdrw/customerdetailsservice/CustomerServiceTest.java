package com.thatdrw.customerdetailsservice;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.thatdrw.customerdetailsservice.entity.Customer;
import com.thatdrw.customerdetailsservice.exception.EntityNotFoundException;
import com.thatdrw.customerdetailsservice.repository.CustomerRepository;
import com.thatdrw.customerdetailsservice.service.CustomerServiceImpl;

@RunWith(MockitoJUnitRunner.class)
public class CustomerServiceTest {

    @Mock
    private CustomerRepository customerRepository;

    @InjectMocks
    private CustomerServiceImpl customerService;

    @Test
    public void getCustomersFromRepoTest() {
        Customer customer = new Customer("first","last",22,"12 abcstreet, 1234ab abdam");
        Customer customer2 = new Customer("second","last",22,"12 abcstreet, 1234ab abdam");
        List<Customer> customerlist = Arrays.asList(customer, customer2);
        when(customerRepository.findAll()).thenReturn(customerlist);
        
        List<Customer> result = customerService.getCustomers();
        
        assertEquals(customerlist, result);
    }
    
    @Test
    public void getSetCustomerFieldsTest() {
        Customer customer = new Customer("first","last",22,"12 abcstreet, 1234ab abdam");
        Customer customer1 = new Customer("please","over",1,"write");

        customer1.setFirstName(customer.getFirstName());
        customer1.setLastName(customer.getLastName());
        customer1.setAge(customer.getAge());
        customer1.setAddress(customer.getAddress());
    }
    
    @Test
    public void findByFirstOrLastNameContainsTest() {
        Customer customer = new Customer("first","last",22,"12 abcstreet, 1234ab abdam");
        Customer customer2 = new Customer("second","last",22,"12 abcstreet, 1234ab abdam");
        when(customerRepository.findByFirstNameContainsOrLastNameContains("last", "last")).thenReturn(Optional.of(Arrays.asList(customer, customer2)));
        
        List<Customer> result = customerService.findCustomer("last");
        
        assertEquals("first", result.get(0).getFirstName());
    }    
    
    @Test
    public void findByInvalidNameTest() {
        Exception thrown = assertThrows(EntityNotFoundException.class, () -> {
            customerService.findCustomer("invalid");
        });
        
        assertEquals("The customer with id '-1' does not exist in our records", thrown.getMessage());
    }

    @Test
    public void findByInvalidIdTest() {
        Exception thrown = assertThrows(EntityNotFoundException.class, () -> {
            customerService.getCustomer(123L);
        });
        
        assertEquals("The customer with id '123' does not exist in our records", thrown.getMessage());
    }
    
    //Find by valid id
    @Test
    public void findByValidId() {
        Customer customer = new Customer("first","last",22,"12 abcstreet, 1234ab abdam");
        when(customerRepository.findById(1L)).thenReturn(Optional.of(customer));

        Customer result = customerService.getCustomer(1L);

        assertEquals(customer, result);
    }

    //Update customer address
    @Test
    public void updateCustomerAddressTest() {
        Customer customer = new Customer("first","last",22,"12 abcstreet, 1234ab abdam");
        Customer updatedcustomer = new Customer("first","last",22,"updated address");
        when(customerRepository.findById(1L)).thenReturn(Optional.of(customer));
        when(customerRepository.save(customer)).thenReturn(updatedcustomer);

        Customer result = customerService.updateCustomerAddress(1L, "updated address");

        assertEquals("updated address", result.getAddress());
    }


    //Unwrap customer
    @Test
    public void unwrapCustomerTest() {
        Customer customer = new Customer("first","last",22,"12 abcstreet, 1234ab abdam");
        Optional<Customer> optcustomer = Optional.of(customer);
        Optional<Customer> emptyOptCustomer = Optional.empty();

        Customer result = CustomerServiceImpl.unwrapCustomer(optcustomer, customer.getId());
        assertEquals(customer, result);
        assertThrows(EntityNotFoundException.class, () -> {
            CustomerServiceImpl.unwrapCustomer(emptyOptCustomer, -1L);
        });

    }

    @Test
    public void unwrapCustomerListTest() {
        Customer customer = new Customer("first","last",22,"12 abcstreet, 1234ab abdam");
        Customer customer2 = new Customer("first","last",22,"12 abcstreet, 1234ab abdam");
        List<Customer> customerlist = Arrays.asList(customer, customer2);

        Optional<List<Customer>> optcustomer = Optional.of(customerlist);
        Optional<List<Customer>> emptyOptCustomer = Optional.empty();

        List<Customer> result = CustomerServiceImpl.unwrapCustomerList(optcustomer, customerlist.get(0).getId());
        assertEquals(customerlist, result);
        assertThrows(EntityNotFoundException.class, () -> {
            CustomerServiceImpl.unwrapCustomerList(emptyOptCustomer, -1L);
        });

    }

}    

