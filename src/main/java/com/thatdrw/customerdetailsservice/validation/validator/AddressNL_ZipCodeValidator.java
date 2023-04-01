package com.thatdrw.customerdetailsservice.validation.validator;

import java.util.regex.Pattern;

import com.thatdrw.customerdetailsservice.validation.annotation.AddressNL_ZipCode;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class AddressNL_ZipCodeValidator implements ConstraintValidator <AddressNL_ZipCode, String> {

    Pattern zipCodePattern = Pattern.compile("^[0-9]{4}[a-zA-Z]{2}$", Pattern.CASE_INSENSITIVE);

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        value = value.replace(" ","");
        boolean isValidZipcode = this.zipCodePattern.matcher(value).find();

        if (isValidZipcode)
            return true;
            
        return false;
    }
    
}
