package com.naturalprogrammer.spring.tutorial.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.naturalprogrammer.spring.tutorial.util.MyUtil;

@Controller
public class HelloController {

    @RequestMapping("/hello")
	public String hello(Model model) {
    	
    	model.addAttribute("name", MyUtil.getMessage("name"));

    	return "hello";
	}
}
