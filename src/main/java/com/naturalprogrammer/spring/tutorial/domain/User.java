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

import com.naturalprogrammer.spring.tutorial.validation.Password;
import com.naturalprogrammer.spring.tutorial.validation.UniqueEmail;

@Entity
@Table(name="usr", indexes = {
    @Index(columnList = "email", unique=true)
})
public class User {
	
	public static enum Role {
		UNVERIFIED, BLOCKED, ADMIN
	}

	@Id
	@GeneratedValue
	private long id;
	
	@UniqueEmail
	@Column(nullable = false, length = 250)
	private String email;

	@NotBlank
    @Size(max=100)
	@Column(nullable = false, length = 100)
	private String name;

	@Password
	@Column(nullable = false) // no length because it will be encrypted
	private String password;
	
	@ElementCollection(fetch = FetchType.EAGER)
	private Collection<Role> roles = new HashSet<Role>();

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
}
