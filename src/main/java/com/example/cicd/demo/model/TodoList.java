package com.example.cicd.demo.model;

import java.io.Serializable;
import java.util.Arrays;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

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
	@NotNull
	@Size(min = 1, max = 24)
	private String title;
	
	/** content */
	@NotNull
	@Size(min = 1, max = 120)
	private String content;
	
	/** expiration date */
	@Size(min = 24, max = 24)
	private String expirationDate;
	
	/** create by user id */
	@NotNull
	@Size(min = 1, max = 24)
	private String creator;
	
	/** shared to user id */
	@Size(min = 1, max = 120)
	private String[] sharedTo;
	
	@Override
	public String toString() {
		return "TodoList [title=" + title + ", content=" + content + ", expirationDate=" + expirationDate + ", creator="
				+ creator + ", sharedTo=" + Arrays.toString(sharedTo) + ", super.toString()=" + super.toString() + "]";
	}
	
}
