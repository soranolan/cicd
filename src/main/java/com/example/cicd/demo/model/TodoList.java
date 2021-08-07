package com.example.cicd.demo.model;

import java.io.Serializable;

import org.springframework.data.mongodb.core.mapping.Document;

import com.example.cicd.core.model.BaseDocument;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Document(collection = "TodoList")
public class TodoList extends BaseDocument implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	/** title */
	private String title;
	
	/** content */
	private String content;
	
	/** expiration date */
	private String expirationDate;
	
	/** create by user id */
	private String creator;
	
	/** shared to user id */
	private String[] sharedTo;
	
}
