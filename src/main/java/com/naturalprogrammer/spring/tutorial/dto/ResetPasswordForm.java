package com.naturalprogrammer.spring.tutorial.dto;

import com.naturalprogrammer.spring.tutorial.validation.Password;
import com.naturalprogrammer.spring.tutorial.validation.RetypePassword;

@RetypePassword
public class ResetPasswordForm {
	
	@Password
	private String password;
	
	@Password
	private String retypePassword;

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRetypePassword() {
		return retypePassword;
	}

	public void setRetypePassword(String retypePassword) {
		this.retypePassword = retypePassword;
	}	
}
