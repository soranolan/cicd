package com.example.cicd.core.model;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.example.cicd.core.enums.Role;
import com.example.cicd.core.misc.CustomAuthorityDeserializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Document(collection = "User")
public class User extends BaseDocument implements UserDetails {
	
	private static final long serialVersionUID = 1L;
	
	@NotNull
	@Size(min = 1, max = 24)
	@Pattern(regexp = "^[a-zA-Z0-9]*$")
	private String username;
	
	@NotNull
	@Size(min = 1, max = 24)
	private String password;
	
	private boolean enabled;
	
	private List<Role> roles;
	
	@Override
	public boolean isAccountNonExpired() {
		return false;
	}
	
	@Override
	public boolean isAccountNonLocked() {
		return false;
	}
	
	@Override
	public boolean isCredentialsNonExpired() {
		return false;
	}
	
	@Override
	public boolean isEnabled() {
		return enabled;
	}
	
	@JsonDeserialize(using = CustomAuthorityDeserializer.class)
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return roles.stream()
					.map(authority -> new SimpleGrantedAuthority(authority.name()))
					.collect(Collectors.toList());
	}
	
	@Override
	public String toString() {
		return "User [username=" + username + ", enabled=" + enabled + ", roles=" + roles + 
				", super.toString()=" + super.toString() + "]";
	}
	
}
