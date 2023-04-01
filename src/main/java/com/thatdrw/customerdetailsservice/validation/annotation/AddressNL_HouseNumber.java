package com.thatdrw.customerdetailsservice.validation.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.thatdrw.customerdetailsservice.validation.validator.AddressNL_HouseNumberValidator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = AddressNL_HouseNumberValidator.class)
public @interface AddressNL_HouseNumber {
    String message() default "Invalid House Number.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
