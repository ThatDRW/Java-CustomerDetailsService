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

	@Override
	public void run(String... args) throws Exception {
		Address testAddress1 = new Address("TestingStreet","123","1234AB","ThatVille");
		Address testAddress2 = new Address("TestingStreet","123","1234AB","ThatVille");
		Address testAddress3 = new Address("TestingStreet","123","1234AB","ThatVille");
		Address testAddress4 = new Address("TestingStreet","123","1234AB","ThatVille");
		Address testAddress5 = new Address("TestingStreet","123","1234AB","ThatVille");
		Address testAddress6 = new Address("TestingStreet","123","1234AB","ThatVille");


		Customer[] customers = new Customer[] {
			new Customer("Josh", "Doe", new Date("Jan 21, 2000"), testAddress1),
			new Customer("Jerry", "Doe", new Date("Feb 12, 1985"), testAddress2),
			new Customer("Sonic", "Hedgehog, the", new Date("Aug 9, 1967"), testAddress3),
			new Customer("Miles Tails", "Prower", new Date("Jun 14, 1985"), testAddress4),
			new Customer("Amy", "Rose", new Date("Dec 30, 1999"), testAddress5),
			new Customer("Shadow", "Hedgehog, the", new Date("May 21, 1006"), testAddress6),
		};
		// Customer[] customers = new Customer[] {
		// 	new Customer("Josh", "Doe", new Date("Jan 21, 2000"), "12 AppleStreet, 15555 ThatVille"),
		// 	new Customer("Jerry", "Doe", new Date("Feb 12, 1985"), "12 AppleStreet, 15555 ThatVille"),
		// 	new Customer("Sonic", "Hedgehog, the", new Date("Aug 9, 1967"), "Sonic Drive 64, Christmas Island"),
		// 	new Customer("Miles Tails", "Prower", new Date("Jun 14, 1985"), "66 Main Rd., West Side Island"),
		// 	new Customer("Amy", "Rose", new Date("Dec 30, 1999"), "Unknown"),
		// 	new Customer("Shadow", "Hedgehog, the", new Date("May 21, 1006"), "Space Colony ARK"),
		// };

		for (Customer c: customers) {
			customerRepository.save(c);
		}
	}

	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}	

}
