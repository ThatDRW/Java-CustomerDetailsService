package com.thatdrw.customerdetailsservice.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.mockito.Mockito.when;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.thatdrw.customerdetailsservice.entity.Address;
import com.thatdrw.customerdetailsservice.entity.Customer;
import com.thatdrw.customerdetailsservice.exception.EntityNotFoundException;
import com.thatdrw.customerdetailsservice.repository.CustomerRepository;
import com.thatdrw.customerdetailsservice.service.impl.CustomerServiceImpl;

@RunWith(MockitoJUnitRunner.class)
public class CustomerServiceTest {

    @Mock
    private CustomerRepository customerRepository;

    @InjectMocks
    private CustomerServiceImpl customerService;

    private Address address;
    private Customer customer;
    private Customer customer2;

    @Before
    public void setUp() {
        address = new Address("TestingStreet","1233","1234AB","ThatVille");
        customer = new Customer("first","last", newDate("Jan 01, 1971"), address);
        customer2 = new Customer("second","last", newDate("Jan 01, 1971"), address);
    }

    private Date newDate(String dateString) {
        try {
            DateFormat format = new SimpleDateFormat("MMM dd, yyyy");
            return format.parse(dateString);
        } catch (ParseException ex) {
            System.out.println(ex.getMessage());
            ex.printStackTrace();
        }
        return new Date();
	}

    @Test
    public void getCustomersFromRepoTest() {
        List<Customer> customerlist = Arrays.asList(customer, customer2);
        when(customerRepository.findAll()).thenReturn(customerlist);
        
        List<Customer> result = customerService.getCustomers();
        
        assertEquals(customerlist, result);
    }
    
    @Test
    public void getSetCustomerFieldsTest() {
        customer2.setFirstName(customer.getFirstName());
        customer2.setLastName(customer.getLastName());
        customer2.setDateOfBirth(customer.getDateOfBirth());
        customer2.setAddress(customer.getAddress());
    }
    
    @Test
    public void findByFirstOrLastNameContainsTest() {
        when(customerRepository.findByFirstNameContainsOrLastNameContains("last", "last")).thenReturn(Optional.of(Arrays.asList(customer, customer2)));
        
        List<Customer> result = customerService.findCustomer("last");
        
        assertEquals("first", result.get(0).getFirstName());
    }    
    
    @Test
    public void findByInvalidNameTest() {
        Exception thrown = assertThrows(EntityNotFoundException.class, () -> {
            customerService.findCustomer("invalid");
        });
        
        assertEquals("The customer with Id '-1' does not exist in our records.", thrown.getMessage());
    }

    @Test
    public void findByInvalidIdTest() {
        Exception thrown = assertThrows(EntityNotFoundException.class, () -> {
            customerService.getCustomer(123L);
        });
        
        assertEquals("The customer with Id '123' does not exist in our records.", thrown.getMessage());
    }
    
    //Find by valid id
    @Test
    public void findByValidId() {
        when(customerRepository.findById(1L)).thenReturn(Optional.of(customer));

        Customer result = customerService.getCustomer(1L);

        assertEquals(customer, result);
    }

    //Update customer address
    @Test
    public void updateCustomerAddressTest() {
        when(customerRepository.findById(1L)).thenReturn(Optional.of(customer));
        when(customerRepository.save(customer)).thenReturn(customer2);

        Customer result = customerService.updateCustomerAddress(1L, address);

        assertEquals(address, result.getAddress());
    }


    //Unwrap customer
    @Test
    public void unwrapCustomerTest() {
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

