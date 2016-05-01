package com.naturalprogrammer.spring.tutorial.controllers;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/signup")
public class SignupController {

	private static final Log log = LogFactory.getLog(SignupController.class);

	@RequestMapping(method=RequestMethod.GET)
	public String signup() {
		
		return "signup";
	}

	@RequestMapping(method=RequestMethod.POST)
	public String doSignup(
		@RequestParam String email,		
		@RequestParam String name,		
		@RequestParam String password) {
		
		log.info("Email: " + email + "; Name: " + name + "; Password:" + password);

		return "redirect:/";
	}
}
