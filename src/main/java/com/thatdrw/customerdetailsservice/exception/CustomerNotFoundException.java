package com.thatdrw.customerdetailsservice.exception;

public class CustomerNotFoundException extends RuntimeException { 

    public CustomerNotFoundException(Long customerId) {
        super("The details of customer with id: '" + customerId + "' do not exist in our records.");
    }
    
}