package com.thatdrw.customerdetailsservice;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.mockito.Mockito.when;

import java.util.List;
import java.time.LocalDateTime;
import java.util.Arrays;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import com.thatdrw.customerdetailsservice.exception.ErrorResponse;

@RunWith(MockitoJUnitRunner.class)
public class ErrorResponseTest {

    @Test
    public void BasicConstructorTest() {
        List<String> message = Arrays.asList("This is a test.");
        ErrorResponse response = new ErrorResponse(message);

        assertEquals(message, response.getMessage());
    }

    @Test
    public void GetSetTest() {
        List<String> message = Arrays.asList("This is a test.");
        ErrorResponse response = new ErrorResponse(message);

        List<String> newmessage = Arrays.asList("This is a new test!");
        response.setMessage(newmessage);

        assertEquals(newmessage, response.getMessage());

        LocalDateTime time = LocalDateTime.now();
        response.setTimestamp(time);

        assertEquals(time, response.getTimestamp());
    }
    
}
