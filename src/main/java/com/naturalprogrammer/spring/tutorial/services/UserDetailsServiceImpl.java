package com.naturalprogrammer.spring.tutorial.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.naturalprogrammer.spring.tutorial.domain.User;
import com.naturalprogrammer.spring.tutorial.repositories.UserRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
	
	@Autowired	
	private UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String username)
			throws UsernameNotFoundException {
		
		User user = userRepository.findByEmail(username)
			.orElseThrow(() -> new UsernameNotFoundException(username));
		return user;
	}
}
