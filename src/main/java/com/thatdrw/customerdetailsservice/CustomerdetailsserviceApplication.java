package com.thatdrw.customerdetailsservice;

import java.util.Date;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.thatdrw.customerdetailsservice.entity.Address;
import com.thatdrw.customerdetailsservice.entity.Customer;
import com.thatdrw.customerdetailsservice.repository.CustomerRepository;
import com.thatdrw.customerdetailsservice.repository.UserRepository;

import lombok.AllArgsConstructor;

@SpringBootApplication
@AllArgsConstructor
public class CustomerdetailsserviceApplication implements CommandLineRunner {

	UserRepository userRepository;
	CustomerRepository customerRepository;

	public static void main(String[] args) {
		SpringApplication.run(CustomerdetailsserviceApplication.class, args);
	}

	private Address getTestAddress() {
		return new Address("TestingStreet","123","1234AB","ThatVille");
	}

	@Override
	public void run(String... args) throws Exception {

		Customer[] customers = new Customer[] {
			new Customer("Josh", "Doe", new Date("Jan 21, 2000"), getTestAddress()),
			new Customer("Jerry", "Doe", new Date("Feb 12, 1985"), getTestAddress()),
			new Customer("Sonic", "Hedgehog, the", new Date("Aug 9, 1967"), getTestAddress()),
			new Customer("Miles Tails", "Prower", new Date("Jun 14, 1985"), getTestAddress()),
			new Customer("Amy", "Rose", new Date("Dec 30, 1999"), getTestAddress()),
			new Customer("Shadow", "Hedgehog, the", new Date("May 21, 1006"), getTestAddress()),
		};

		for (Customer c: customers) {
			customerRepository.save(c);
		}
	}

	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}	

}
