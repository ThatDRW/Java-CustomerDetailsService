package com.thatdrw.customerdetailsservice;

import java.util.Arrays;

import jakarta.persistence.EntityNotFoundException;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.thatdrw.customerdetailsservice.exception.ErrorResponse;

@ExtendWith(SpringExtension.class)
public class ApplicationExceptionHandlerTest {

    // Strange IntelliSense bug shows unused import if not used directly like this for some reason.
    private com.thatdrw.customerdetailsservice.ApplicationExceptionHandler handler = new com.thatdrw.customerdetailsservice.ApplicationExceptionHandler();

    @Test
    public void handleResourceNotFoundExceptionTest() {
        String message = "EntityNotFound";
        ResponseEntity<Object> handled = handler.handleResourceNotFoundException(new EntityNotFoundException(message));

        Object body = handled.getBody();
        if (body != null)
            assertEquals(Arrays.asList(message), ((ErrorResponse) body).getMessage());
        
        assertEquals(HttpStatus.NOT_FOUND, handled.getStatusCode());
    }
    
    @Test
    public void handleDataAccessExceptionTest() {
        String message = "Cannot delete non-existing resource.";
        ResponseEntity<Object> handled = handler.handleDataAccessException(new EmptyResultDataAccessException(1));
        
        Object body = handled.getBody();
        if (body != null)
            assertEquals(Arrays.asList(message), ((ErrorResponse) body).getMessage());

        assertEquals(HttpStatus.NOT_FOUND, handled.getStatusCode());
    }

    @Test
    public void handleDataIntegrityViolationExceptionTest() {
        String message = "Data Integrity Violation: we cannot process your request.";
        ResponseEntity<Object> handled = handler.handleDataIntegrityViolationException(new DataIntegrityViolationException(""));

        Object body = handled.getBody();
        if (body != null)
            assertEquals(Arrays.asList(message), ((ErrorResponse) body).getMessage());

        assertEquals(HttpStatus.BAD_REQUEST, handled.getStatusCode());
    }
}
