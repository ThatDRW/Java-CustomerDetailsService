package com.thatdrw.customerdetailsservice.entity;

import com.thatdrw.customerdetailsservice.validation.annotation.AddressNL_HouseNumber;
import com.thatdrw.customerdetailsservice.validation.annotation.AddressNL_ZipCode;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Address {

    @NotBlank(message = "Street Name cannot be blank.")
    @Size(min = 3, message = "Street Name needs to be longer than 3 characters.")
    private String streetName;

    @AddressNL_HouseNumber(message = "House Number is not valid.")
    @NotBlank(message = "House Number cannot be blank.")
    private String houseNumber;

    @AddressNL_ZipCode(message = "Zip Code is not valid.")
    @NotBlank(message = "Zip Code cannot be blank.")
    private String zipCode;

    @NotBlank(message = "City cannot be blank.")
    @Size(min = 2, message = "City needs to be longer than 2 characters.")
    private String city;

}
