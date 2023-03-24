package com.thatdrw.customerdetailsservice.integration;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.thatdrw.customerdetailsservice.web.CustomerController;
import com.thatdrw.customerdetailsservice.web.UserController;

@SpringBootTest
@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
public class CustomerdetailsserviceApplicationTests {

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

}
