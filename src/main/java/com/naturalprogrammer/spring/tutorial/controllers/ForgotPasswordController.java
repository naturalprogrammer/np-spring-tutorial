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

import com.naturalprogrammer.spring.tutorial.dto.ForgotPasswordForm;
import com.naturalprogrammer.spring.tutorial.services.UserService;
import com.naturalprogrammer.spring.tutorial.util.MyUtil;

@Controller
@RequestMapping("/forgot-password")
public class ForgotPasswordController {
	
	@Autowired
	private UserService userService;

	@GetMapping
	public String forgotPassword(Model model) {
		
		model.addAttribute(new ForgotPasswordForm());		
		return "forgot-password";		
	}

	@PostMapping
	public String forgotPassword(
			@Validated ForgotPasswordForm forgotPasswordForm,
			BindingResult result,
			RedirectAttributes redirectAttributes) {

		if (result.hasErrors())
			return "forgot-password";

		userService.forgotPassword(forgotPasswordForm);
		MyUtil.flash(redirectAttributes, "info",
				"checkMailResetPassword");

		return "redirect:/";
	}
}
