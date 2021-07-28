package com.example.cicd.core.model;

import java.io.Serializable;

import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Document(collection = "User")
public class User extends BaseDocument implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private String account;
	
	private String password;
	
}
