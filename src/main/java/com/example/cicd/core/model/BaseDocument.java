package com.example.cicd.core.model;

import java.io.Serializable;

import org.springframework.data.annotation.Id;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BaseDocument implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	/** primary key */
	@Id
	private String id;
	
	/** create date time */
	private String createdAt;
	
	/** update date time */
	private String updatedAt;
	
	/** create by user id */
	private String creator;
	
	/** shared to user id */
	private String[] sharedTo;
	
}
