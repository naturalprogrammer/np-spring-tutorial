package com.naturalprogrammer.spring.tutorial;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.web.config.EnableSpringDataWebSupport;

@SpringBootApplication
@EnableSpringDataWebSupport
public class NpSpringTutorialApplication {

	public static void main(String[] args) {
		SpringApplication.run(NpSpringTutorialApplication.class, args);
	}
}
