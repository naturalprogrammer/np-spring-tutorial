package com.naturalprogrammer.spring.tutorial.services;

import javax.annotation.PostConstruct;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.naturalprogrammer.spring.tutorial.domain.User;
import com.naturalprogrammer.spring.tutorial.domain.User.Role;
import com.naturalprogrammer.spring.tutorial.repositories.UserRepository;

@Service
@Transactional(propagation=Propagation.SUPPORTS, readOnly=true)
public class UserServiceImpl implements UserService {

	private static final Log log = LogFactory.getLog(UserServiceImpl.class);

	@PostConstruct
	public void postConstruct() {
		log.info("UserServiceImpl constructed");
	}

	@Autowired
	private UserRepository userRepository;
	
    @Value("${app.admin.email:admin@example.com}")
    private String adminEmail;
	
    @Value("${app.admin.name:First Admin}")
    private String adminName;

    @Value("${app.admin.password:password}")
    private String adminPassword;

    @Override
	@EventListener
	@Transactional(propagation=Propagation.REQUIRED, readOnly=false)
	public void afterApplicationReady(ApplicationReadyEvent event) {

		if (!userRepository.findByEmail(adminEmail).isPresent()) {

			User user = new User();

			user.setEmail(adminEmail);
			user.setName(adminName);
			user.setPassword(adminPassword);

			user.getRoles().add(Role.ADMIN);

			userRepository.save(user);			
		}		
	}
	
	@Override
	@Transactional(propagation=Propagation.REQUIRED, readOnly=false)
	public void signup(User user) {

		user.getRoles().add(Role.UNVERIFIED);
		userRepository.save(user);
	}
}
