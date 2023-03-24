package com.thatdrw.customerdetailsservice.integration;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.thatdrw.customerdetailsservice.entity.User;
import com.thatdrw.customerdetailsservice.web.CustomerController;
import com.thatdrw.customerdetailsservice.web.UserController;

@SpringBootTest
@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
public class UserControllerTest {

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

    

    @Test
    public void createUserTest() throws Exception {
        String randomusername = "user" + (Math.random() * 1000);
        User user = new User(randomusername ,"pass");

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter writer = mapper.writer().withDefaultPrettyPrinter();

        String json = writer.writeValueAsString(user);

        RequestBuilder request = MockMvcRequestBuilders.post("/user/register")
                                                        .contentType(MediaType.APPLICATION_JSON)
                                                        .content(json);

        mockMvc.perform(request).andExpect(status().isCreated());
    }

    @Test
    public void findByIdNoJWTAuthTest() throws Exception {
        RequestBuilder request = MockMvcRequestBuilders.get("/user/1");

        mockMvc.perform(request).andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser
    public void findByIdCustomerNotFoundTest() throws Exception {
        RequestBuilder request = MockMvcRequestBuilders.get("/user/12345");

        mockMvc.perform(request).andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser
    public void findByIdTest() throws Exception {
        RequestBuilder request = MockMvcRequestBuilders.get("/user/1");       
        
        // Run this test to populate the Users table with a user to retrieve.
        createUserTest();

        mockMvc.perform(request).andExpect(status().isOk());
    }
    
}
