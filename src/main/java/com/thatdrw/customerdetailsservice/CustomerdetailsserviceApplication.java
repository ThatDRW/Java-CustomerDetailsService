package com.thatdrw.customerdetailsservice;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

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
		Customer[] customers = new Customer[] {
			new Customer("Josh", "Doe", 22, "12 AppleStreet, 15555 ThatVille"),
			new Customer("Jerry", "Doe", 48, "12 AppleStreet, 15555 ThatVille"),
			new Customer("Sonic", "Hedgehog, the", 15, "Sonic Drive 64, Christmas Island"),
			new Customer("Miles Tails", "Prower", 8, "66 Main Rd., West Side Island"),
			new Customer("Amy", "Rose", 12, "Unknown"),
			new Customer("Shadow", "Hedgehog, the", 15, "Space Colony ARK"),
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
