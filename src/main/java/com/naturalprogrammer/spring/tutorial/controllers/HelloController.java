package com.naturalprogrammer.spring.tutorial.controllers;

import java.util.Optional;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class HelloController {

	@RequestMapping("/hello/{id}")
	public String hello(Model model,
		@PathVariable String id,
		@RequestParam Optional<String> name) {
		
		model.addAttribute("id", id);	
		model.addAttribute("name", name.orElse("No Name"));	

		return "hello";
	}
}
