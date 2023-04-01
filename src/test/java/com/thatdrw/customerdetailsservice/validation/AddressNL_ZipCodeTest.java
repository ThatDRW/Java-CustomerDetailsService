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
public class AddressNL_ZipCodeTest {

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
    public void OnlyValidZipCodeTest() {
        String[] zipCodes = {"1234AB","1234 AB","1234  AB","9999 ZZ"};

        for (String zipCode: zipCodes) {
            address.setZipCode(zipCode);
    
            Set<ConstraintViolation<Address>> violations = validator.validate(address);

            for (ConstraintViolation<Address> violation: violations) {
                System.out.println(violation.getMessage());
            }

            assertTrue(violations.isEmpty());
        }
    }

    @Test
    public void OnlyInvalidZipCodeTest() {
        String[] zipCodes = {"1111","123434","AA 1234","BB BBBB","what"};

        for (String zipCode: zipCodes) {
            address.setZipCode(zipCode);

            Set<ConstraintViolation<Address>> violations = validator.validate(address);

            for (ConstraintViolation<Address> violation: violations) {
                System.out.println(violation.getMessage());
            }
            System.out.println(zipCode);
            assertFalse(violations.isEmpty());
        }
    }
}
