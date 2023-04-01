package com.thatdrw.customerdetailsservice.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.thatdrw.customerdetailsservice.validation.annotation.AddressNL_HouseNumber;
import com.thatdrw.customerdetailsservice.validation.annotation.AddressNL_ZipCode;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "customer_address")
@Getter
@Setter
@RequiredArgsConstructor
@NoArgsConstructor
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    
    @NotBlank(message = "Street Name cannot be blank.")
    @Size(min = 3, message = "Street Name needs to be longer than 3 characters.")
    @NonNull
    @Column(name = "street_name", nullable = false)
    private String streetName;

    @AddressNL_HouseNumber(message = "House Number is not valid.")
    @NotBlank(message = "House Number cannot be blank.")
    @NonNull
    @Column(name = "house_number", nullable = false)
    private String houseNumber;

    @AddressNL_ZipCode(message = "Zip Code is not valid.")
    @NotBlank(message = "Zip Code cannot be blank.")
    @NonNull
    @Column(name = "zip_code", nullable = false)
    private String zipCode;

    @NotBlank(message = "City cannot be blank.")
    @Size(min = 2, message = "City needs to be longer than 2 characters.")
    @NonNull
    @Column(name = "city", nullable = false)
    private String city;

    @JsonIgnore
    @OneToOne(mappedBy = "address")
    private Customer customer;

    public String toString() {
        return streetName + " " + houseNumber + ", " + zipCode + " " + city;
    }

}
