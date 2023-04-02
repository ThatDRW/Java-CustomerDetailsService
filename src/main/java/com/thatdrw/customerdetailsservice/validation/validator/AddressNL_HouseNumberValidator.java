package com.thatdrw.customerdetailsservice.validation.validator;

import java.util.regex.Pattern;

import com.thatdrw.customerdetailsservice.validation.annotation.AddressNL_HouseNumber;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class AddressNL_HouseNumberValidator implements ConstraintValidator <AddressNL_HouseNumber, String> {

    Pattern houseNumbersPattern = Pattern.compile("^[0-9]+[0-9a-zA-Z/.-]*$", Pattern.CASE_INSENSITIVE);

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) 
            return false;
        
        value = value.replace(" ", "");
        boolean isValid = this.houseNumbersPattern.matcher(value).find();

        if (isValid)
            return true;
            
        return false;
    }
    
}
