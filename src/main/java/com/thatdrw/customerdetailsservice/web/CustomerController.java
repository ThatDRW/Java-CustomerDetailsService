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

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping("/customer")
public class CustomerController {
    
    CustomerService customerService;

    @Operation(summary = "Add customer", description = "Adds a new customer. Id is provided automatically.")
    @ApiResponse(responseCode = "201", description = "Customer created succesfully.", content = @Content(array = @ArraySchema(schema = @Schema(implementation = Customer.class))))
    @ApiResponse(responseCode = "403", description = "Operation requires Auth.")
    @PostMapping
    public ResponseEntity<Customer> saveCustomer(@Valid @RequestBody Customer customer) {
        return new ResponseEntity<>(customerService.saveCustomer(customer), HttpStatus.CREATED);
    }
    
    @Operation(summary = "Find all customers", description = "Retrieve list of all customers.")
    @ApiResponse(responseCode = "200", description = "Succesful retrieval of all customers details.", content = @Content(array = @ArraySchema(schema = @Schema(implementation = Customer.class))))
    @ApiResponse(responseCode = "403", description = "Operation requires Auth.")
    @ApiResponse(responseCode = "404", description = "No customers not found.")
    @GetMapping("/all")
    public ResponseEntity<List<Customer>> getCustomers() {
        return new ResponseEntity<>(customerService.getCustomers(), HttpStatus.OK);
    }
    
    @Operation(summary = "Find customer by id", description = "Retrieve customer details by id.")
    @ApiResponse(responseCode = "200", description = "Succesful retrieval of customer details.", content = @Content(array = @ArraySchema(schema = @Schema(implementation = Customer.class))))
    @ApiResponse(responseCode = "403", description = "Operation requires Auth.")
    @ApiResponse(responseCode = "404", description = "Customer not found.")
    @GetMapping("/{id}")
    public ResponseEntity<Customer> getCustomer(@PathVariable Long id) {
        return new ResponseEntity<>(customerService.getCustomer(id), HttpStatus.OK);
    }
    
    @Operation(summary = "Find customer by name", description = "Retrieve customer(s) details containing the query in name. Case sensitive.")
    @ApiResponse(responseCode = "200", description = "Succesful retrieval of customer details.", content = @Content(array = @ArraySchema(schema = @Schema(implementation = Customer.class))))
    @ApiResponse(responseCode = "403", description = "Operation requires Auth.")
    @ApiResponse(responseCode = "404", description = "Customer not found.")
    @GetMapping("/find/{query}")
    public ResponseEntity<List<Customer>> findCustomer(@PathVariable String query) {
        return new ResponseEntity<>(customerService.findCustomer(query), HttpStatus.OK);
    }
    
    @Operation(summary = "Update customer address", description = "Update customer address with new address provided in body.")
    @ApiResponse(responseCode = "201", description = "Customer address updated succesfully.", content = @Content(array = @ArraySchema(schema = @Schema(implementation = Customer.class))))
    @ApiResponse(responseCode = "403", description = "Operation requires Auth.")
    @ApiResponse(responseCode = "404", description = "Customer not found.")
    @PostMapping("/{id}/updateAddress")
    public ResponseEntity<Customer> updateAddress(@PathVariable Long id, @RequestBody String address) {
        Customer customer = customerService.getCustomer(id);
        customer.setAddress(address);
        return new ResponseEntity<>(customerService.saveCustomer(customer), HttpStatus.OK);
    }
}
