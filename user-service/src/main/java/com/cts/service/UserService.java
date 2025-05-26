package com.cts.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.cts.client.AuthClient;
import com.cts.client.LoggingClient;
import com.cts.client.NotificationClient;
import com.cts.entity.Email;
import com.cts.entity.User;
import com.cts.exception.UserAlreadyExistsException;
import com.cts.repository.UserRepo;

@Service
public class UserService {

	@Autowired
	private LoggingClient loggingClient;
	
	@Autowired
	UserRepo userRepo;

	@Autowired
	NotificationClient notificationClient;
	
	@Autowired
	AuthClient authClient;

	private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);

	public String addNewUser(String username, String password, String email, String role) {
		User user = new User();
        if("not verified".equals(authClient.verifyUsercredentials(email)))
        {
        	return "user with invalid credentials";
        }
		user.setName(username);
		user.setPassword(encoder.encode(password));
		user.setEmail(email);
		user.setRole(role);

		if (userRepo.findByEmail(email) != null) {
			throw new UserAlreadyExistsException("Email already exists!");
		}
        notificationClient.email(
        		new Email(
        				user.getName(),
        				user.getEmail(),
        				"🎉 Welcome to E-learning platform!",
        				"Dear "+user.getName()+",\r\n"
        				+ "\r\n"
        				+ "Welcome to E-learning platform! We’re excited to have you join our community.\r\n"
        				+ "\r\n"
        				+ "Your account has been successfully created, and you’re now part of a learning journey filled with amazing opportunities. Whether you’re here to gain new skills, explore interesting topics, or connect with experts, we’re thrilled to support you along the way."));
		userRepo.save(user);
		loggingClient.logMessage("INFO","User service added user");
		return "user has been added successfully";
	}

	public String checkLogin(String email, String password) {
		User user = userRepo.findByEmail(email);
		if (user == null) {
			return "user not found";
		} else if (!encoder.matches(password, user.getPassword())) {
			return "incorrect password";
		}
		if (user != null && encoder.matches(password, user.getPassword())) {
			// System.out.println("This is the user name " + user.getName());

			if ("STUDENT".equals(user.getRole())) {
				return "okStudent";
			} else if ("INSTRUCTOR".equals(user.getRole())) {
				return "okInstructor";
			}

		}
		return "fail";

	}

	public String forgotPassword(String email) {
		User user = userRepo.findByEmail(email);
		if (user != null) {
			Email email1 = new Email();
			email1.setTo(email);
			email1.setName(user.getName());
			email1.setSubject("E-Learning Reset Password Mail");
			email1.setBody("http://localhost:8083/resetpassword");
			notificationClient.email(email1);
			loggingClient.logMessage("INFO","User service sent Email to "+email+" for Resetting password ");
			// notificationClient.s(email, ,"Click the link to reset your
			// password","http://localhost:8083/resetpassword");
			return "found";
		}
		return "notfound";
	}

	public String resetPassword(String email1, String newpassword) {
		User user = userRepo.findByEmail(email1);
		if (user != null) {
			user.setPassword(encoder.encode(newpassword));
			loggingClient.logMessage("INFO","User service successfully changed password for Email to "+email1);
			userRepo.save(user);
			return "passwordchanged";
		}
		loggingClient.logMessage("INFO", "Password not Changed for User "+email1);
		return "passwordnotchanged";
	}
	
	public User getUserById(int id)
	{
		User user = userRepo.findById(id).orElse(null);
		loggingClient.logMessage("INFO", user.toString());
		return user;
	}
	


}
