package com.naturalprogrammer.spring.tutorial.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.support.TransactionSynchronizationAdapter;
import org.springframework.transaction.support.TransactionSynchronizationManager;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.naturalprogrammer.spring.tutorial.domain.User;

@Component
public class MyUtil {
	
	private static MessageSource messageSource;
	
	@Autowired
	public MyUtil(MessageSource messageSource) {
		MyUtil.messageSource = messageSource;
	}
	
	public static String getMessage(String messageKey, Object... args) {
		
		return messageSource.getMessage(messageKey, args,
				LocaleContextHolder.getLocale());
	}
	
	public static void flash(RedirectAttributes redirectAttributes,
			String kind, String messageKey) {
			
		redirectAttributes.addFlashAttribute("flashKind", kind);
		redirectAttributes.addFlashAttribute("flashMessage",
					MyUtil.getMessage(messageKey));
	}
	
	public static User getUser() {

		// get the authentication object
		Authentication auth = SecurityContextHolder
				.getContext().getAuthentication();

		// get the user from the authentication object
		if (auth != null) {
			Object principal = auth.getPrincipal();
			if (principal instanceof User) {
				return (User) principal;
			}
		}
		return null;	  
	}
	
	public static void login(User user) {
		
	    // make the authentication object
	    Authentication authentication =
	    	new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
	    
	    // put that in the security context
	    SecurityContextHolder.getContext().setAuthentication(authentication);
	}
	
	public static void logout() {
		SecurityContextHolder.getContext().setAuthentication(null);
	}
	
	public static void afterCommit(Runnable runnable) {
		TransactionSynchronizationManager.registerSynchronization(
		    new TransactionSynchronizationAdapter() {
		        @Override
		        public void afterCommit() {
		        	runnable.run();
		        }
		});				
	}

	public static void validate(boolean valid,
			String messageKey, Object... messageArguments) {

		if (!valid)
			throw new RuntimeException(getMessage(messageKey, messageArguments));
	}
}
