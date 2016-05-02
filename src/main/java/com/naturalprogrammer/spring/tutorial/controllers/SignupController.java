package com.naturalprogrammer.spring.tutorial.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.naturalprogrammer.spring.tutorial.domain.User;
import com.naturalprogrammer.spring.tutorial.services.UserService;

@Controller
@RequestMapping("/signup")
public class SignupController {

	@Autowired
	private UserService userService;

	@RequestMapping(method=RequestMethod.GET)
	public String signup(Model model) {
		
		model.addAttribute(new User());
		return "signup";
	}

	@RequestMapping(method=RequestMethod.POST)
	public String doSignup(@Validated User user, BindingResult result) {
		
		if (result.hasErrors())
			return "signup";

		userService.signup(user);

		return "redirect:/";
	}
}
