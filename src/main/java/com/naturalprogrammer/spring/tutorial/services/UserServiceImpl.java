package com.naturalprogrammer.spring.tutorial.services;

import java.util.UUID;

import javax.annotation.PostConstruct;
import javax.mail.MessagingException;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.naturalprogrammer.spring.tutorial.domain.User;
import com.naturalprogrammer.spring.tutorial.domain.User.Role;
import com.naturalprogrammer.spring.tutorial.dto.ForgotPasswordForm;
import com.naturalprogrammer.spring.tutorial.mail.MailSender;
import com.naturalprogrammer.spring.tutorial.repositories.UserRepository;
import com.naturalprogrammer.spring.tutorial.util.MyUtil;

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

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Value("${application.url}")
	private String applicationUrl;
		
	@Autowired
	private MailSender mailSender;

	@Override
	@EventListener
	@Transactional(propagation=Propagation.REQUIRED, readOnly=false)
	public void afterApplicationReady(ApplicationReadyEvent event) {

		if (!userRepository.findByEmail(adminEmail).isPresent()) {

			User user = new User();

			user.setEmail(adminEmail);
			user.setName(adminName);
			user.setPassword(adminPassword);
			user.setPassword(passwordEncoder.encode(adminPassword));

			user.getRoles().add(Role.ADMIN);

			userRepository.save(user);			
		}		
	}
	
	@Override
	@Transactional(propagation=Propagation.REQUIRED, readOnly=false)
	public void signup(User user) {

		user.setPassword(passwordEncoder.encode(user.getPassword()));
		user.getRoles().add(Role.UNVERIFIED);
		user.setVerificationCode(UUID.randomUUID().toString());
		userRepository.save(user);
		
		MyUtil.afterCommit(() -> {
			
			MyUtil.login(user);
			sendVerificationMail(user);
		});
	}
	
	private void sendVerificationMail(User user) {
		try {

			// make the link
			String verifyLink = applicationUrl
					+ "/users/" + user.getVerificationCode() + "/verify";

			// send the mail
			mailSender.send(user.getEmail(),
					MyUtil.getMessage("verifySubject"),
					MyUtil.getMessage("verifyEmail", verifyLink));

		} catch (MessagingException e) {
			// In case of exception, just log the error and keep silent
			log.error(ExceptionUtils.getStackTrace(e));
		}
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRED, readOnly=false)
	public void verify(String verificationCode) {
		
		// get the current-user from the session
		User currentUser = MyUtil.getUser();
		
		// fetch a fresh copy from the database
		User user = userRepository.findOne(currentUser.getId());
		
		// ensure that the user is unverified
		MyUtil.validate(user.getRoles().contains(Role.UNVERIFIED),
				"alreadyVerified");	
		
		// ensure that the verification code of the user matches
	      // with the given one
		MyUtil.validate(verificationCode.equals(user.getVerificationCode()),
				"wrongVerificationCode");
		
		makeVerified(user); // make him verified
		userRepository.save(user);
		
		// after successful commit,
		MyUtil.afterCommit(() -> {
			
			// Re-login the user, so that the UNVERIFIED role is removed
			MyUtil.login(user);
		});
	}

	private void makeVerified(User user) {
		user.getRoles().remove(Role.UNVERIFIED);
		user.setVerificationCode(null);
	}

	@Override
	public void resendVerificationMail(User user) {
		
		// The user must exist
		MyUtil.validate(user != null, "userNotFound");
		
		// Only self or ADMINs allowed
		MyUtil.validate(user.isAdminOrSelfLoggedIn(), "notPermitted");

		// must be unverified
		MyUtil.validate(user.getRoles().contains(Role.UNVERIFIED),
				"alreadyVerified");	

		// send the verification mail
		sendVerificationMail(user);
	}	

	@Override
	@Transactional(propagation=Propagation.REQUIRED, readOnly=false)
	public void forgotPassword(ForgotPasswordForm forgotPasswordForm) {
		
		// fetch the user record from database
		User user = userRepository.findByEmail(forgotPasswordForm.getEmail()).get();

		// set a reset password code
		user.setResetPasswordCode(UUID.randomUUID().toString());
		userRepository.save(user);

		// after successful commit, mail him a link to reset his password
		MyUtil.afterCommit(() -> mailResetPasswordLink(user));
	}

	private void mailResetPasswordLink(User user) {
		
		try {

			// make the link
			String resetPasswordLink =	applicationUrl
				    + "/reset-password/" + user.getResetPasswordCode();
			
			// send the mail
			mailSender.send(user.getEmail(),
					MyUtil.getMessage("resetPasswordSubject"),
					MyUtil.getMessage("resetPasswordEmail",
						resetPasswordLink));
			
		} catch (MessagingException e) {
			// In case of exception, just log the error and keep silent			
			log.error(ExceptionUtils.getStackTrace(e));
		}
	}
	
	@Override
	@Transactional(propagation=Propagation.REQUIRED, readOnly=false)
	public void resetPassword(String resetPasswordCode,
			String password) {
		
		User user = userRepository.findByResetPasswordCode(resetPasswordCode).get();
		user.setPassword(passwordEncoder.encode(password));
		user.setResetPasswordCode(null);
		userRepository.save(user);
	}

	@Override
	public User findById(long userId) {
		
		User user = userRepository.findOne(userId);
		MyUtil.validate(user != null, "userNotFound");

		if (!user.isAdminOrSelfLoggedIn())
			user.setEmail("Confidential");
			
		return user;
	}
	
	@Override
	@Transactional(propagation=Propagation.REQUIRED, readOnly=false)
	public void update(User user, User updatedData) {
		
		// Ensure that a user with the given id exists
		MyUtil.validate(user != null, "userNotFound");
		
		// Only self or an admin can edit the profile data
		MyUtil.validate(user.isAdminOrSelfLoggedIn(), "notPermitted");

		// Update the name
		user.setName(updatedData.getName());
		
		User loggedIn = MyUtil.getUser(); 

		// Only an admin can edit roles
		if (loggedIn.isAdmin())
			user.setRoles(updatedData.getRoles());
		
		// save the updates
		userRepository.save(user);

		MyUtil.afterCommit(() -> {

			// If the logged in user is editing his own profile,
			// log her in again, so that Spring Security principal
			// gets updated
	            if (loggedIn.getId() == user.getId())
	        	    MyUtil.login(user);
	    });
	}
}
