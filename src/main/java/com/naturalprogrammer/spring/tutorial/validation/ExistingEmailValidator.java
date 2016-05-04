package com.naturalprogrammer.spring.tutorial.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.naturalprogrammer.spring.tutorial.repositories.UserRepository;

@Component
public class ExistingEmailValidator
implements ConstraintValidator<ExistingEmail, String> {

	@Autowired
	private UserRepository userRepository;
	
	@Override
	public void initialize(ExistingEmail constraintAnnotation) {
		// initialization code
	}

	@Override
	public boolean isValid(String email, ConstraintValidatorContext context) {
		
		return userRepository.findByEmail(email).isPresent();
	}
}
