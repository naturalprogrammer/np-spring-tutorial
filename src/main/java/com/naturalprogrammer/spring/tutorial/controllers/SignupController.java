package com.naturalprogrammer.spring.tutorial.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.naturalprogrammer.spring.tutorial.domain.User;
import com.naturalprogrammer.spring.tutorial.services.UserService;

@Controller
@RequestMapping("/signup")
public class SignupController {

	@Autowired
	private UserService userService;

    @GetMapping
	public String signup(Model model) {
		
		model.addAttribute(new User());
		return "signup";
	}

    @PostMapping
	public String doSignup(@Validated User user, BindingResult result) {
		
		if (result.hasErrors())
			return "signup";

		userService.signup(user);

		return "redirect:/";
	}
}
