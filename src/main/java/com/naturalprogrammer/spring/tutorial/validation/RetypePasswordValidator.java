package com.naturalprogrammer.spring.tutorial.validation;

import java.util.Objects;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.stereotype.Component;

import com.naturalprogrammer.spring.tutorial.dto.ResetPasswordForm;

@Component
public class RetypePasswordValidator
implements ConstraintValidator<RetypePassword, ResetPasswordForm> {
	
	@Override
	public boolean isValid(ResetPasswordForm retypePasswordForm,
		ConstraintValidatorContext context) {
		
		if (!Objects.equals(retypePasswordForm.getPassword(),
				        retypePasswordForm.getRetypePassword())) {
			
			// Moving the error from form-level to
			// field-level property: retypePassword
			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate(
				"{passwordsDoNotMatch}")
			      .addPropertyNode("retypePassword").addConstraintViolation();
			
			return false;
		}

		return true;
	}

	@Override
	public void initialize(RetypePassword constraintAnnotation) {
		// initialization code
	}
}
