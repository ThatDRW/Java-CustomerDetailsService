package com.thatdrw.customerdetailsservice.entity;

import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.PositiveOrZero;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

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

    @PositiveOrZero(message = "Age has to be 0 or above.")
    @NonNull
    @Column(name = "age", nullable = false)
    private Integer age;

    @NotBlank(message = "Address cannot be blank.")
    @NonNull
    @Column(name = "address", nullable = false)
    private String address;

}
