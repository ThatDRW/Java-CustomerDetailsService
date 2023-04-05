package com.thatdrw.customerdetailsservice.validation;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import com.thatdrw.customerdetailsservice.entity.Address;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

@ExtendWith(MockitoExtension.class)
public class AddressNL_HouseNumberTest {

    private Validator validator;
    private Address address;
    private String[] houseNumbersValid;
    private String[] houseNumbersInvalid;

    @BeforeEach
    public void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();

        address = new Address("Test Street", "1234", "1234AB", "ThatVille");
        houseNumbersValid = new String[] {"9001","1","192a","12-14", "12/II", "1 apt. 1"};
        houseNumbersInvalid = new String[] {"a123a","afh","x&$","","11,3"};
    }

    @Test
    public void OnlyValidHouseNumberTest() {
        for (String houseNumber: houseNumbersValid) {
            address.setHouseNumber(houseNumber);
    
            Set<ConstraintViolation<Address>> violations = validator.validate(address);

            assertTrue(violations.isEmpty());
        }
    }

    @Test
    public void OnlyInvalidHouseNumberTest() {
        for (String houseNumber: houseNumbersInvalid) {
            address.setHouseNumber(houseNumber);

            Set<ConstraintViolation<Address>> violations = validator.validate(address);

            assertFalse(violations.isEmpty());
        }
    }
}
