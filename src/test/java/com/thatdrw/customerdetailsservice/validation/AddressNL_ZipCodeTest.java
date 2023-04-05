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
public class AddressNL_ZipCodeTest {

    private Validator validator;
    private Address address;
    private String[] zipCodesValid;
    private String[] zipCodesInvalid;

    @BeforeEach
    public void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();

        address = new Address("Test Street", "1234", "1234AB", "ThatVille");
        zipCodesValid = new String[] {"1234AB","1234 AB","1234  AB","9999 ZZ"};
        zipCodesInvalid = new String[] {"1111","123434","AA 1234","BB BBBB","what"};
    }

    @Test
    public void OnlyValidZipCodeTest() {
        for (String zipCode: zipCodesValid) {
            address.setZipCode(zipCode);
    
            Set<ConstraintViolation<Address>> violations = validator.validate(address);

            assertTrue(violations.isEmpty());
        }
    }

    @Test
    public void OnlyInvalidZipCodeTest() {
        for (String zipCode: zipCodesInvalid) {
            address.setZipCode(zipCode);

            Set<ConstraintViolation<Address>> violations = validator.validate(address);
            
            assertFalse(violations.isEmpty());
        }
    }
}
