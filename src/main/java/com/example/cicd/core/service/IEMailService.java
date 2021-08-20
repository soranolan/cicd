package com.example.cicd.core.service;

import com.example.cicd.core.model.User;

public interface IEMailService {
	
	/**
	 * send mail
	 * 
	 * @param to email receiver
	 * @param subject title
	 * @param text content
	 */
	public void sendMail(String to, String subject, String text);
	
	/**
	 * send account activate mail
	 * 
	 * @param user user object (receiver)
	 */
	public void sendActivateLink(User user);
	
}
