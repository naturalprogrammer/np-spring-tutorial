package com.naturalprogrammer.spring.tutorial.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private UserDetailsService userDetailsService;

	@Override
	protected void configure(AuthenticationManagerBuilder builder)
			throws Exception {

		builder.userDetailsService(userDetailsService);
	}

	@Override
    protected void configure(HttpSecurity http) throws Exception {
    	
        http
        	.authorizeRequests()
		    	.antMatchers(HttpMethod.GET,
		    			"/").permitAll()
		    	.antMatchers(
		    			"/signup",
		    			"/forgot-password",
	            		"/reset-password/*").permitAll()
		    	.antMatchers(HttpMethod.GET, "/admin/**").hasRole("ADMIN")
            	.anyRequest().authenticated()
	        .and()
	            .formLogin()
		            .loginPage("/login").permitAll()
		    .and()
		       .logout().permitAll();
    }
}
