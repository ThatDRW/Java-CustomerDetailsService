package com.thatdrw.customerdetailsservice.integration;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
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

        RequestBuilder request = MockMvcRequestBuilders.post(SecurityConstants.REGISTER_PATH)
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
    
    @Test
    public void bearerJWTFilterTests() throws Exception {
        //#region Create User
        String randomusername = "user" + (Math.random() * 1000);
        User user = new User(randomusername ,"pass");

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter writer = mapper.writer().withDefaultPrettyPrinter();

        String json = writer.writeValueAsString(user);

        RequestBuilder request = MockMvcRequestBuilders.post(SecurityConstants.REGISTER_PATH)
                                                        .contentType(MediaType.APPLICATION_JSON)
                                                        .content(json);

        mockMvc.perform(request).andExpect(status().isCreated());
        //#endregion

        //#region Authenticate and retrieve Bearer Token
        RequestBuilder authRequest = MockMvcRequestBuilders.post(SecurityConstants.AUTH_PATH)
                                                            .contentType(MediaType.APPLICATION_JSON)
                                                            .content(json);

        MvcResult authResult = mockMvc.perform(authRequest)
                                       .andExpect(status().isOk())
                                       .andReturn();
        
        String bearerToken = authResult.getResponse().getHeader(SecurityConstants.AUTHORIZATION);
        //#endregion

        // TEST: JWTAuthorizationFilter Bearer Token Parsing
        RequestBuilder JWTAuthFilterTestRequest = MockMvcRequestBuilders
                                                   .get("/customer/all")
                                                   .header(SecurityConstants.AUTHORIZATION, bearerToken);

        mockMvc.perform(JWTAuthFilterTestRequest).andExpect(status().isOk());

        // TEST: ExceptionHandlerFilter
        //      - Invalid Token
        RequestBuilder invalidTokenTestRequest = MockMvcRequestBuilders
                                                  .get("/customer/all")
                                                  .header(SecurityConstants.AUTHORIZATION, bearerToken + "fake");
        
        mockMvc.perform(invalidTokenTestRequest).andExpect(status().isForbidden());

        //      - Invalid Username
        User invalidUser = new User("invalidusername" ,"pass");

        ObjectMapper invMapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter invWriter = invMapper.writer().withDefaultPrettyPrinter();

        String invJson = invWriter.writeValueAsString(invalidUser);

        RequestBuilder invRequest = MockMvcRequestBuilders.post(SecurityConstants.AUTH_PATH)
                                                            .contentType(MediaType.APPLICATION_JSON)
                                                            .content(invJson);

        mockMvc.perform(invRequest).andExpect(status().isNotFound());

        //      - Bad Request
        RequestBuilder badRequest = MockMvcRequestBuilders.post(SecurityConstants.AUTH_PATH);

        mockMvc.perform(badRequest).andExpect(status().isBadRequest());
    }
}
