package com.thatdrw.customerdetailsservice.validation.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.thatdrw.customerdetailsservice.validation.validator.AddressNL_ZipCodeValidator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = AddressNL_ZipCodeValidator.class)
public @interface AddressNL_ZipCode {
    String message() default "Invalid ZIP Code.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
