package com.naturalprogrammer.spring.tutorial.services;

import org.springframework.boot.context.event.ApplicationReadyEvent;

import com.naturalprogrammer.spring.tutorial.domain.User;

public interface UserService {

	void signup(User user);
	void afterApplicationReady(ApplicationReadyEvent event);
}
