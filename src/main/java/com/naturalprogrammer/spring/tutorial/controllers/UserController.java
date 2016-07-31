package com.naturalprogrammer.spring.tutorial.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.naturalprogrammer.spring.tutorial.domain.User;
import com.naturalprogrammer.spring.tutorial.services.UserService;
import com.naturalprogrammer.spring.tutorial.util.MyUtil;

@Controller
@RequestMapping("/users")
public class UserController {
	
	@Autowired
	private UserService userService;

    @GetMapping("/{verificationCode}/verify")
    public String verify(@PathVariable String verificationCode,
    		RedirectAttributes redirectAttributes) {
    	
		userService.verify(verificationCode);
		MyUtil.flash(redirectAttributes, "success", "verificationSuccess");

		return "redirect:/";
    }
    
    @GetMapping("/{id}/resend-verification-mail")
    public String resendVerificationMail(@PathVariable("id") User user,
    		RedirectAttributes redirectAttributes) {

    	userService.resendVerificationMail(user);
    	
    	MyUtil.flash(redirectAttributes,
    			"success", "verificationMailSent");
    	
    	return "redirect:/";
    }
}
