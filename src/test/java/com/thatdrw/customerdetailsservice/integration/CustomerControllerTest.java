package com.thatdrw.customerdetailsservice.integration;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.thatdrw.customerdetailsservice.entity.Address;
import com.thatdrw.customerdetailsservice.entity.Customer;
import com.thatdrw.customerdetailsservice.web.CustomerController;
import com.thatdrw.customerdetailsservice.web.UserController;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
public class CustomerControllerTest {

    @Autowired
    private UserController userController;

    @Autowired
    private CustomerController customerController;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private MockMvc mockMvc;

    private String json;

    @BeforeEach
    public void setUp() throws Exception {
        json = generateCustomerJson();
    }

    @Test
    public void contextLoads() {
        assertNotNull(userController);
        assertNotNull(customerController);
        assertNotNull(bCryptPasswordEncoder);
        assertNotNull(mockMvc);
    }

    private Date newDate(String dateString) throws ParseException {
        DateFormat format = new SimpleDateFormat("MMM dd, yyyy");
		return format.parse(dateString);
	}

    private String mapToJson(Object object) {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter writer = mapper.writer().withDefaultPrettyPrinter();
        try {
            return writer.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return "JsonProcessingException thrown.";
        }
    }
    
    public String generateCustomerJson() throws Exception {
        Address address = new Address("TestingStreet", "1233", "1234AB", "ThatVille");

        Customer customer = new Customer("first","last", newDate("Feb 21, 1995"), address);

        return this.mapToJson(customer);
    }

    public void addMockCustomerToRepo() throws Exception {
        RequestBuilder request = MockMvcRequestBuilders.post("/customer")
                                                        .contentType(MediaType.APPLICATION_JSON)
                                                        .content(json);

        mockMvc.perform(request).andExpect(status().isCreated());
    }

    @Test
    public void saveCustomerNoJWTAuthTest() throws Exception {
        RequestBuilder request = MockMvcRequestBuilders.post("/customer")
                                                        .contentType(MediaType.APPLICATION_JSON)
                                                        .content(json);

        mockMvc.perform(request).andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser
    public void saveCustomerTest() throws Exception {
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

        Address newAddress = new Address("ThisNewStreet","21","1234AB","HeyThere");

        RequestBuilder request = MockMvcRequestBuilders.post("/customer/1/updateAddress")
                                                        .contentType(MediaType.APPLICATION_JSON)
                                                        .content(this.mapToJson(newAddress));

        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.address.streetName").value("ThisNewStreet"));
    }
    
}
