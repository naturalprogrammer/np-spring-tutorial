package com.naturalprogrammer.spring.tutorial.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
}
