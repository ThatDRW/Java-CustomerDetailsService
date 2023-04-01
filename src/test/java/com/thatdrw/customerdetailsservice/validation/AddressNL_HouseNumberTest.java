package com.thatdrw.customerdetailsservice.validation;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import com.thatdrw.customerdetailsservice.entity.Address;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

@RunWith(MockitoJUnitRunner.class)
public class AddressNL_HouseNumberTest {

    private Validator validator;
    private Address address;

    @Before
    public void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();

        address = new Address();
        address.setStreetName("Test Street");
        address.setHouseNumber("1234");
        address.setZipCode("1234AB");
        address.setCity("ThatVille");
    }

    @Test
    public void OnlyValidHouseNumberTest() {
        String[] houseNumbers = {"9001","1234","1","0"};

        for (String houseNumber: houseNumbers) {
            address.setHouseNumber(houseNumber);
    
            Set<ConstraintViolation<Address>> violations = validator.validate(address);

            for (ConstraintViolation<Address> violation: violations) {
                System.out.println(violation.getMessage());
            }

            assertTrue(violations.isEmpty());
        }
    }

    @Test
    public void OnlyInvalidHouseNumberTest() {
        String[] houseNumbers = {"123a","afh","x&$"};

        for (String houseNumber: houseNumbers) {
            address.setHouseNumber(houseNumber);

            Set<ConstraintViolation<Address>> violations = validator.validate(address);

            for (ConstraintViolation<Address> violation: violations) {
                System.out.println(violation.getMessage());
            }

            assertFalse(violations.isEmpty());
        }
    }
}
