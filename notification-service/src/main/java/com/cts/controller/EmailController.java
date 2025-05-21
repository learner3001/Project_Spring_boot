package com.cts.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cts.entity.Email;
import com.cts.service.EmailService;

@RequestMapping("/email")
@RestController
public class EmailController {

	@Autowired
	EmailService emailService;
	
	@PostMapping("/emailService")
	public ResponseEntity<String> email(@RequestBody Email email)
	{
		
		return ResponseEntity.ok(emailService.sendEmail(email));
	}
}
