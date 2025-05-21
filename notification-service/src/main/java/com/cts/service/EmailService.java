package com.cts.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.cts.entity.Email;

@Service
public class EmailService {

	@Autowired
	private JavaMailSender mailSender;
	
	@Value("${spring.mail.username}")
	private String mailId;

	public String sendEmail(Email email) {
		SimpleMailMessage message = new SimpleMailMessage();
		message.setFrom(mailId);
		message.setTo(email.getTo());
		message.setText(email.getBody());
		message.setSubject(email.getSubject());
		mailSender.send(message);
		return "mail sent";

	}
}
