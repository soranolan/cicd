package com.example.cicd.core.model;

import java.io.Serializable;

import javax.validation.constraints.Size;

import org.springframework.data.annotation.Id;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BaseDocument implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	/** primary key */
	@Id
	@Size(min = 1, max = 24)
	private String id;
	
	/** create date time */
	@Size(min = 29, max = 29)
	private String createdAt;
	
	/** update date time */
	@Size(min = 29, max = 29)
	private String updatedAt;
	
	@Override
	public String toString() {
		return "BaseDocument [id=" + id + ", createdAt=" + createdAt + ", updatedAt=" + updatedAt + "]";
	}
	
}
