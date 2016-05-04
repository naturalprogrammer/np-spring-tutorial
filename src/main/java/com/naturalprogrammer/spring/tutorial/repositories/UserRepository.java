package com.naturalprogrammer.spring.tutorial.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.naturalprogrammer.spring.tutorial.domain.User;

public interface UserRepository  extends JpaRepository<User, Long> {

	Optional<User> findByEmail(String email);
	Optional<User> findByResetPasswordCode(String resetPasswordCode);
}
