package com.thatdrw.customerdetailsservice.validation.validator;

import java.util.regex.Pattern;

import com.thatdrw.customerdetailsservice.validation.annotation.AddressNL_HouseNumber;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class AddressNL_HouseNumberValidator implements ConstraintValidator <AddressNL_HouseNumber, String> {

    Pattern onlyNumbersPattern = Pattern.compile("^[0-9]+$", Pattern.CASE_INSENSITIVE);

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        boolean isOnlyNumbers = this.onlyNumbersPattern.matcher(value).find();

        if (isOnlyNumbers)
            return true;
            
        return false;
    }
    
}
