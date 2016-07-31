package com.naturalprogrammer.spring.tutorial.controllers;

import javax.mail.MessagingException;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.naturalprogrammer.spring.tutorial.mail.MailSender;

@RestController
public class MailController {

	private MailSender mailSender;
	
	public MailController(MailSender mailSender) {
		this.mailSender = mailSender;
	}

	@RequestMapping("/mail")
	public String sendMail() throws MessagingException {
		
		mailSender.send("abc@example.com", "Some subject", "the content");
		
		return "Mail sent";
	}
}
