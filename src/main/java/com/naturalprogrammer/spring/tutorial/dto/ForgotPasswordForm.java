package com.naturalprogrammer.spring.tutorial.dto;

import com.naturalprogrammer.spring.tutorial.validation.ExistingEmail;

public class ForgotPasswordForm {

	@ExistingEmail
	private String email;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}	
}
