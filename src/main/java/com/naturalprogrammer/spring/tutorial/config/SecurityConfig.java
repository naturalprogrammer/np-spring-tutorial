package com.naturalprogrammer.spring.tutorial.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.rememberme.TokenBasedRememberMeServices;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Value("${rememberMeKey:topSecret}")
	private String rememberMeKey;

	@Autowired
	private UserDetailsService userDetailsService;

	@Bean
	public PasswordEncoder passwordEncoder() {
	  return new BCryptPasswordEncoder();
	}

	@Override
    protected void configure(HttpSecurity http) throws Exception {
    	
        http
        	.authorizeRequests()
		    	.mvcMatchers(HttpMethod.GET,
		    			"/users/*",
		    			"/").permitAll()
		    	.mvcMatchers(
		    			"/signup",
		    			"/forgot-password",
	            		"/reset-password/*").permitAll()
		    	.mvcMatchers(HttpMethod.GET, "/admin/**").hasRole("ADMIN")
            	.anyRequest().authenticated()
	        .and()
	            .formLogin()
		            .loginPage("/login").permitAll()
		    .and()
		        .logout().permitAll()
		    .and()
	     		.rememberMe()
		     		.key(rememberMeKey)
		     		.rememberMeServices(new TokenBasedRememberMeServices(rememberMeKey, userDetailsService));
    }
}
