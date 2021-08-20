package com.example.cicd.core.model;

import static java.util.stream.Collectors.toList;

import java.util.Collection;
import java.util.List;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.example.cicd.core.enums.Role;
import com.example.cicd.core.misc.CustomAuthorityDeserializer;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import lombok.Getter;
import lombok.Setter;

@JsonIgnoreProperties({ "accountNonExpired", "accountNonLocked", "credentialsNonExpired", "enabled" })
@Getter
@Setter
@Document(collection = "User")
public class User extends BaseDocument implements UserDetails {
	
	private static final long serialVersionUID = 1L;
	
	/** user name, user id, account id */
	@NotNull
	@Size(min = 1, max = 24)
	@Pattern(regexp = "^[a-zA-Z0-9]*$")
	private String username;
	
	/** password */
	@NotNull
	@Size(min = 1, max = 24)
	private String password;
	
	/** email address */
	private String email;
	
	/** is account activated by email */
	private String isActivated;
	
	/** temp login system error message */
	private String systemMessage;
	
	/** is enabled */
	private boolean enabled;
	
	/** user roles */
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
					.collect(toList());
	}
	
	@Override
	public String toString() {
		return "User [username=" + username + ", isActivated=" + isActivated + ", roles=" + roles + 
				", super.toString()=" + super.toString() + "]";
	}
	
}
