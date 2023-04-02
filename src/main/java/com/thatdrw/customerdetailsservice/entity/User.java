package com.thatdrw.customerdetailsservice.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "users")
/*
 * 'user' is a reserved keyword in SQL, so we name our table users. If you name it user, you will get a org.h2.jdbc.JdbcSQLSyntaxErrorException. 
 *  See https://docs.microsoft.com/en-us/sql/t-sql/language-elements/reserved-keywords-transact-sql?view=sql-server-ver16 for a list of reserved keywords.
 */
@Getter
@Setter
@RequiredArgsConstructor
@NoArgsConstructor
public class User {

    @Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@NotBlank(message =  "Username cannot be blank.")
	@Size(min = 2, message = "Username has to be at least 2 characters long.")
	@NonNull
	@Column(nullable = false, unique = true)
	private String username;
	
	@NotBlank(message =  "Password cannot be blank.")
	@Size(min = 2, message = "Password has to be at least 2 characters long.")
    @NonNull
	@Column(nullable = false)
	private String password;

}