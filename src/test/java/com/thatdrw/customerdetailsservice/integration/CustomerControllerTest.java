package com.thatdrw.customerdetailsservice.integration;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Date;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.thatdrw.customerdetailsservice.entity.Customer;
import com.thatdrw.customerdetailsservice.web.CustomerController;
import com.thatdrw.customerdetailsservice.web.UserController;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
public class CustomerControllerTest {

    @Autowired
    private UserController userController;

    @Autowired
    private CustomerController customerController;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void contextLoads() {
        assertNotNull(userController);
        assertNotNull(customerController);
        assertNotNull(bCryptPasswordEncoder);
        assertNotNull(mockMvc);
    }

    
    public String generateCustomerJson() throws Exception {
        Customer customer = new Customer("first","last", new Date("Feb 21, 1995"),"123 Test St. 1234 Utrecht");

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter writer = mapper.writer().withDefaultPrettyPrinter();

        return writer.writeValueAsString(customer);
    }

    public void addMockCustomerToRepo() throws Exception {
        String json = generateCustomerJson();

        RequestBuilder request = MockMvcRequestBuilders.post("/customer")
                                                        .contentType(MediaType.APPLICATION_JSON)
                                                        .content(json);

        mockMvc.perform(request).andExpect(status().isCreated());
    }

    @Test
    public void saveCustomerNoJWTAuthTest() throws Exception {
        String json = generateCustomerJson();

        RequestBuilder request = MockMvcRequestBuilders.post("/customer")
                                                        .contentType(MediaType.APPLICATION_JSON)
                                                        .content(json);

        mockMvc.perform(request).andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser
    public void saveCustomerTest() throws Exception {
        String json = generateCustomerJson();

        RequestBuilder request = MockMvcRequestBuilders.post("/customer")
                                                        .contentType(MediaType.APPLICATION_JSON)
                                                        .content(json);

        mockMvc.perform(request).andExpect(status().isCreated());
    }

    @Test
    @WithMockUser
    public void getCustomersTest() throws Exception {
        addMockCustomerToRepo();
        
        RequestBuilder request = MockMvcRequestBuilders.get("/customer/all");

        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    @WithMockUser
    public void findCustomerByIdTest() throws Exception {
        addMockCustomerToRepo();

        RequestBuilder request = MockMvcRequestBuilders.get("/customer/1");

        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value("1"));
    }

    @Test
    @WithMockUser
    public void findCustomerByNameTest() throws Exception {
        addMockCustomerToRepo();

        RequestBuilder request = MockMvcRequestBuilders.get("/customer/find/Doe");

        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    @WithMockUser
    public void findUnknownCustomerByNameTest() throws Exception {
        addMockCustomerToRepo();

        RequestBuilder request = MockMvcRequestBuilders.get("/customer/find/ABABABABA");

        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    @WithMockUser
    public void updateCustomerAddressTest() throws Exception {
        addMockCustomerToRepo();

        String newAddress = "This is my new address!";
        RequestBuilder request = MockMvcRequestBuilders.post("/customer/1/updateAddress")
                                                        .contentType(MediaType.TEXT_PLAIN)
                                                        .content(newAddress);

        mockMvc.perform(request)
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.address").value(newAddress));
    }
    
}
