package com.naturalprogrammer.spring.tutorial.controllers;

import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.naturalprogrammer.spring.tutorial.dto.ResetPasswordForm;
import com.naturalprogrammer.spring.tutorial.services.UserService;
import com.naturalprogrammer.spring.tutorial.util.MyUtil;

@Controller
@RequestMapping("/reset-password/{resetPasswordCode}")
public class ResetPasswordController {
	
	@Autowired
	private UserService userService;

	@GetMapping
	public String forgotPassword(Model model) {
		
		model.addAttribute(new ResetPasswordForm());		
		return "reset-password";		
	}	

	@PostMapping
	public String resetPassword(
			@PathVariable String resetPasswordCode,
			@Validated ResetPasswordForm resetPasswordForm,
			BindingResult result,
			RedirectAttributes redirectAttributes) {
	
		if (result.hasErrors())
			return "reset-password";
	
		try {
			userService.resetPassword(resetPasswordCode,
					resetPasswordForm.getPassword());
			MyUtil.flash(redirectAttributes,
					"success", "passwordChanged");
			return "redirect:/login";
		} catch (NoSuchElementException e) {
			result.reject("invalidUrl");
			return "reset-password";
		}
	}
}
