package com.thatdrw.customerdetailsservice.exception;


public class EntityNotFoundException extends RuntimeException { 

    public EntityNotFoundException(Long id, Class<?> entity) { 
            super("The " + entity.getSimpleName().toLowerCase() + " with Id '" + id + "' does not exist in our records.");
    }

}