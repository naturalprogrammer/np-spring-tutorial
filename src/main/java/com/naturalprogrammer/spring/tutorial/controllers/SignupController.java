package com.naturalprogrammer.spring.tutorial.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.naturalprogrammer.spring.tutorial.domain.User;
import com.naturalprogrammer.spring.tutorial.services.UserService;
import com.naturalprogrammer.spring.tutorial.util.MyUtil;

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
	public String doSignup(
			@Validated(User.SignUpValidation.class) User user,
			BindingResult result,
			RedirectAttributes redirectAttributes) {
		
		if (result.hasErrors())
			return "signup";

		userService.signup(user);
		MyUtil.flash(redirectAttributes, "success", "signupSuccess");
		
		return "redirect:/";
	}
}
