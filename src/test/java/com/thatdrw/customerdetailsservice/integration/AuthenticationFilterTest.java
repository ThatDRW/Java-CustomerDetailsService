package com.thatdrw.customerdetailsservice.integration;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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
public class AuthenticationFilterTest {

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
    public void attemptAuthenticationTestValid() throws Exception {
        String randomusername = "user" + (Math.random() * 1000);
        String json = createUserJson(randomusername,"pass");
        addMockUserToRepo(json);

        RequestBuilder request = MockMvcRequestBuilders.post("/authenticate")
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

        RequestBuilder request = MockMvcRequestBuilders.post("/authenticate")
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
        RequestBuilder request = MockMvcRequestBuilders.post("/user/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json);
    
        mockMvc.perform(request).andExpect(status().isCreated());
    }
}
