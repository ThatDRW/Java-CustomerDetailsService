package com.thatdrw.customerdetailsservice;

import java.text.ParseException;
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

	private Date newDate(String dateString) throws ParseException {
		return java.text.DateFormat.getDateInstance().parse(dateString);
	}

	@Override
	public void run(String... args) throws Exception {

		Customer[] customers = new Customer[] {
			new Customer("Josh", "Doe", newDate("Jan 21, 2000"), getTestAddress()),
			new Customer("Jerry", "Doe", newDate("Feb 12, 1985"), getTestAddress()),
			new Customer("Sonic", "Hedgehog, the", newDate("Aug 9, 1967"), getTestAddress()),
			new Customer("Miles Tails", "Prower", newDate("Jun 14, 1985"), getTestAddress()),
			new Customer("Amy", "Rose", newDate("Dec 30, 1999"), getTestAddress()),
			new Customer("Shadow", "Hedgehog, the", newDate("May 21, 1006"), getTestAddress()),
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
