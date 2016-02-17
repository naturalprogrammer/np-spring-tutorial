package com.naturalprogrammer.spring.tutorial.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.naturalprogrammer.spring.tutorial.mail.MailSender;
import com.naturalprogrammer.spring.tutorial.mail.MockMailSender;

@RestController
public class MailController {

	private MailSender mailSender = new MockMailSender();
	
	@RequestMapping("/mail")
	public String sendMail() {
		
		mailSender.send("abc@example.com", "Some subject", "the content");
		
		return "Mail sent";
	}
}
