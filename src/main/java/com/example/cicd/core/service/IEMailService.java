package com.example.cicd.core.service;

import com.example.cicd.core.model.User;

public interface IEMailService {
	
	public void sendMail(String to, String subject, String text);
	
	public void sendActivateLink(User user);
	
}
