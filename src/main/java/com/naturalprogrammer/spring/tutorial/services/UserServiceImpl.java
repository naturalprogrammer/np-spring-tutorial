package com.naturalprogrammer.spring.tutorial.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.naturalprogrammer.spring.tutorial.domain.User;
import com.naturalprogrammer.spring.tutorial.domain.User.Role;
import com.naturalprogrammer.spring.tutorial.repositories.UserRepository;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;

	@Override
	public void signup(User user) {

		user.getRoles().add(Role.UNVERIFIED);
		userRepository.save(user);
	}
}
