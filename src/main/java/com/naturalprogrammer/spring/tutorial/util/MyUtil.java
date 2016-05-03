package com.naturalprogrammer.spring.tutorial.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
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

}
