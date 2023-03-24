package com.thatdrw.customerdetailsservice.unit;

import java.util.Arrays;

import jakarta.persistence.EntityNotFoundException;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.test.context.junit4.SpringRunner;

import com.thatdrw.customerdetailsservice.ApplicationExceptionHandler;
import com.thatdrw.customerdetailsservice.exception.ErrorResponse;

@RunWith(SpringRunner.class)
public class ApplicationExceptionHandlerTest {

    private ApplicationExceptionHandler handler = new ApplicationExceptionHandler();

    @Test
    public void handleResourceNotFoundExceptionTest() {
        String message = "EntityNotFound";
        ResponseEntity<Object> handled = handler.handleResourceNotFoundException(new EntityNotFoundException(message));
        
        assertEquals(Arrays.asList(message), ((ErrorResponse) handled.getBody()).getMessage());
        assertEquals(HttpStatus.NOT_FOUND, handled.getStatusCode());
    }
    
    @Test
    public void handleDataAccessExceptionTest() {
        String message = "Cannot delete non-existing resource";
        ResponseEntity<Object> handled = handler.handleDataAccessException(new EmptyResultDataAccessException(1));
        
        assertEquals(Arrays.asList(message), ((ErrorResponse) handled.getBody()).getMessage());
        assertEquals(HttpStatus.NOT_FOUND, handled.getStatusCode());
    }

    @Test
    public void handleDataIntegrityViolationExceptionTest() {
        String message = "Data Integrity Violation: we cannot process your request.";
        ResponseEntity<Object> handled = handler.handleDataIntegrityViolationException(new DataIntegrityViolationException(""));

        assertEquals(Arrays.asList(message), ((ErrorResponse) handled.getBody()).getMessage());
        assertEquals(HttpStatus.BAD_REQUEST, handled.getStatusCode());
    }
}
