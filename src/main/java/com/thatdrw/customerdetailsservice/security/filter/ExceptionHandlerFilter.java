package com.thatdrw.customerdetailsservice.security.filter;

import java.io.IOException;
import java.util.Arrays;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.web.filter.OncePerRequestFilter;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.thatdrw.customerdetailsservice.exception.EntityNotFoundException;
import com.thatdrw.customerdetailsservice.exception.ErrorResponse;

public class ExceptionHandlerFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            filterChain.doFilter(request, response);
        } catch (EntityNotFoundException e) { //Feel free to create a separate function.
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            response.getWriter().write(jsonErrorResponse("Username does not exist."));
            response.getWriter().flush();
        } catch (JWTVerificationException e) {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            response.getWriter().write(jsonErrorResponse("Auth Error: Invalid token."));
            response.getWriter().flush();
        } catch (RuntimeException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write(jsonErrorResponse("BAD REQUEST"));
            response.getWriter().flush();
        }  
    }

    private String jsonErrorResponse(String message) {
        ErrorResponse error = new ErrorResponse(Arrays.asList(message));

        // Setup JavaTimeModule for Date parsing.
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());

        ObjectWriter writer = mapper.writer().withDefaultPrettyPrinter();
        
        try {
            String json = writer.writeValueAsString(error);
            return json;
        } catch (JsonProcessingException e) {
            System.err.println("JsonProcessingException caught trying to map an ErrorResponse with message: " + message);
            System.err.println(e.getMessage());
            e.printStackTrace();
        }
        return "";
    }
}
