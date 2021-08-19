package com.example.cicd.core.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.example.cicd.core.model.User;
import com.example.cicd.core.service.IEMailService;
import com.example.cicd.core.util.PasetoUtils;

@Service
public class EMailServiceImpl implements IEMailService {
	
	@Value("${custom.activate.link}")
	private String url;
	
	private JavaMailSender sender;
	
	public EMailServiceImpl(JavaMailSender sender) {
		this.sender = sender;
	}
	
	@Override
	public void sendMail(String to, String subject, String text) {
		SimpleMailMessage message = new SimpleMailMessage();
		message.setTo(to);
		message.setSubject(subject);
		message.setText(text);
		sender.send(message);
	}
	
	@Override
	public void sendActivateLink(User user) {
		String to = user.getEmail();
		String subject = "This is a system generated email, please do not reply";
		String text = "Please click the link below to activate your account.\n\nThe link will be expired in 15 minutes.\n\n";
		Map<String, Object> claims = new HashMap<>();
		claims.put("role", user.getRoles());
		String token = PasetoUtils.compact(user.getUsername(), claims);
		sendMail(to, subject, text + url + token);
	}
	
}
