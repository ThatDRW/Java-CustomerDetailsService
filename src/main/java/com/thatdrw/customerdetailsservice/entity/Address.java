package com.thatdrw.customerdetailsservice.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Address {

    private String streetName;
    private String houseNumber;
    private String zipCode;
    private String city;

}
