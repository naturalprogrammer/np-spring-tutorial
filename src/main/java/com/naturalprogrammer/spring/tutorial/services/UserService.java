package com.naturalprogrammer.spring.tutorial.services;

import org.springframework.boot.context.event.ApplicationReadyEvent;

import com.naturalprogrammer.spring.tutorial.domain.User;
import com.naturalprogrammer.spring.tutorial.dto.ForgotPasswordForm;

public interface UserService {

	void signup(User user);
	void afterApplicationReady(ApplicationReadyEvent event);
	void verify(String verificationCode);
	void resendVerificationMail(User user);
	void forgotPassword(ForgotPasswordForm forgotPasswordForm);
	void resetPassword(String resetPasswordCode, String password);
	User findById(long userId);
	void update(User user, User updatedData);
}
