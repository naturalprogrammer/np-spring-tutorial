package com.naturalprogrammer.spring.tutorial.domain;

import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;

public class User {
	
	@NotBlank(message = "{blankEmail}")
	@Email
	@Size(min=4, max=250, message = "{emailSize}")
	private String email;

	@NotBlank
    @Size(max=100)
	private String name;

	@NotBlank
    @Size(min=6, max=40)
	private String password;
	
	// Getters and Setters

	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}	
}
