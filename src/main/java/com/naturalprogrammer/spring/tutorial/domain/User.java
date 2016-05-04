package com.naturalprogrammer.spring.tutorial.domain;

import java.util.Collection;
import java.util.HashSet;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.naturalprogrammer.spring.tutorial.util.MyUtil;
import com.naturalprogrammer.spring.tutorial.validation.Password;
import com.naturalprogrammer.spring.tutorial.validation.UniqueEmail;

@Entity
@Table(name="usr", indexes = {
    @Index(columnList = "email", unique=true)
})
public class User implements UserDetails {
	
	// Validation groups
	public static interface SignUpValidation {}
	public static interface UpdateValidation {}

	private static final long serialVersionUID = 8426932111132668753L;

	public static enum Role {
		UNVERIFIED, BLOCKED, ADMIN
	}

	@Id
	@GeneratedValue
	private long id;
	
	@UniqueEmail(groups = SignUpValidation.class)
	@Column(nullable = false, length = 250)
	private String email;
	
	
	@NotBlank(groups = {SignUpValidation.class, UpdateValidation.class})
    @Size(max=100, groups = {SignUpValidation.class, UpdateValidation.class})
	@Column(nullable = false, length = 100)
	private String name;
	
	@Password(groups = SignUpValidation.class)
	@Column(nullable = false) // no length because it will be encrypted
	private String password;
	
	@ElementCollection(fetch = FetchType.EAGER)
	private Collection<Role> roles = new HashSet<Role>();

	@Column(length = 36, unique=true)
	private String verificationCode;

	@Column(length = 36, unique=true)
	private String resetPasswordCode;

	// Getters and Setters

	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	@Override
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}	
	public Collection<Role> getRoles() {
		return roles;
	}
	public void setRoles(Collection<Role> roles) {
		this.roles = roles;
	}
	public String getVerificationCode() {
		return verificationCode;
	}
	public void setVerificationCode(String verificationCode) {
		this.verificationCode = verificationCode;
	}
	public String getResetPasswordCode() {
		return resetPasswordCode;
	}
	public void setResetPasswordCode(String resetPasswordCode) {
		this.resetPasswordCode = resetPasswordCode;
	}
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		Collection<GrantedAuthority> authorities =
				new HashSet<GrantedAuthority>(roles.size());

		for (Role role: roles)
			authorities.add(new SimpleGrantedAuthority(
					"ROLE_" + role.name()));

		return authorities;
	}
	
	@Override
	public String getUsername() {
		return email;
	}
	
	public boolean isAdminOrSelfLoggedIn() {
		
		User loggedIn = MyUtil.getUser();
		
		if (loggedIn == null) // nobody logged in
			return false;
		
		if (loggedIn.isAdmin()) // an Admin has logged in
			return true;
		
		if (loggedIn.getId() == id) // Same user has logged in
			return true;
		
		return false; // some other user has logged in
	}

	public boolean isAdmin() {
		return roles.contains(Role.ADMIN);
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}
	@Override
	public boolean isAccountNonLocked() {
		return true;
	}
	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}
	@Override
	public boolean isEnabled() {
		return true;
	}
}
