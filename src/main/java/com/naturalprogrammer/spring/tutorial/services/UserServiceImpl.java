package com.naturalprogrammer.spring.tutorial.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.naturalprogrammer.spring.tutorial.domain.User;
import com.naturalprogrammer.spring.tutorial.domain.User.Role;
import com.naturalprogrammer.spring.tutorial.repositories.UserRepository;

@Service
@Transactional(propagation=Propagation.SUPPORTS, readOnly=true)
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;

	@Override
	@Transactional(propagation=Propagation.REQUIRED, readOnly=false)
	public void signup(User user) {

		user.getRoles().add(Role.UNVERIFIED);
		userRepository.save(user);
	}
}
