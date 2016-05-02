package com.naturalprogrammer.spring.tutorial.controllers;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.naturalprogrammer.spring.tutorial.domain.User;

@Controller
@RequestMapping("/signup")
public class SignupController {

	private static final Log log = LogFactory.getLog(SignupController.class);

	@RequestMapping(method=RequestMethod.GET)
	public String signup() {
		
		return "signup";
	}

	@RequestMapping(method=RequestMethod.POST)
	public String doSignup(User user) {
		
		log.info("Email: " + user.getEmail()
			+ "; Name: " + user.getName()
			+ "; Password:" + user.getPassword());

		return "redirect:/";
	}
}
