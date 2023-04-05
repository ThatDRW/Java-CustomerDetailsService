package com.thatdrw.customerdetailsservice.integration;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.thatdrw.customerdetailsservice.entity.User;
import com.thatdrw.customerdetailsservice.security.SecurityConstants;
import com.thatdrw.customerdetailsservice.web.CustomerController;
import com.thatdrw.customerdetailsservice.web.UserController;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
public class AuthenticationFilterTest {

    @Autowired
    private UserController userController;

    @Autowired
    private CustomerController customerController;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private MockMvc mockMvc;

    private String randomusername;
    private String json;

    @BeforeEach
    public void setUp() throws Exception {
        randomusername = "user" + (Math.random() * 1000);
        json = createUserJson(randomusername,"pass");
    }

    @Test
    public void contextLoads() {
        assertNotNull(userController);
        assertNotNull(customerController);
        assertNotNull(bCryptPasswordEncoder);
        assertNotNull(mockMvc);
    }

    @Test
    public void attemptAuthenticationTestValid() throws Exception {
        addMockUserToRepo(json);

        RequestBuilder request = MockMvcRequestBuilders.post(SecurityConstants.AUTH_PATH)
                                                        .contentType(MediaType.APPLICATION_JSON)
                                                        .content(json);

        mockMvc.perform(request).andExpect(status().isOk());

    }

    @Test
    public void attemptAuthenticationTestInvalid() throws Exception {
        String randomusername = "user" + (Math.random() * 1000);
        String json = createUserJson(randomusername, "pass");
        addMockUserToRepo(json);

        String invalidjson = createUserJson(randomusername, "invalidpass");

        RequestBuilder request = MockMvcRequestBuilders.post(SecurityConstants.AUTH_PATH)
                                                        .contentType(MediaType.APPLICATION_JSON)
                                                        .content(invalidjson);

        mockMvc.perform(request).andExpect(status().isUnauthorized());

    }
    
    public String createUserJson(String username, String password) throws Exception {
        User user = new User(username, password);
        
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter writer = mapper.writer().withDefaultPrettyPrinter();
    
        return writer.writeValueAsString(user);
    }
    
    public void addMockUserToRepo(String json) throws Exception {
        RequestBuilder request = MockMvcRequestBuilders.post(SecurityConstants.REGISTER_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json);
    
        mockMvc.perform(request).andExpect(status().isCreated());
    }
}
