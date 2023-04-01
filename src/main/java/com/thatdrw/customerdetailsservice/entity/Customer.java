package com.thatdrw.customerdetailsservice.entity;

import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;

@Entity
@Table(name = "customers")
@Getter
@Setter
@RequiredArgsConstructor
@NoArgsConstructor
public class Customer {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotBlank(message = "First Name cannot be blank.")
    @NonNull
    @Column(name = "first_name", nullable = false)
    private String firstName;

    @NotBlank(message = "Last Name cannot be blank.")
    @NonNull
    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Past(message = "Date of Birth has to be in the past.")
    @NonNull
    @Column(name = "date_of_birth", nullable = false)
    private Date dateOfBirth;

    @NotBlank(message = "Address cannot be blank.")
    @NonNull
    @Column(name = "address", nullable = false)
    private String address;

}
