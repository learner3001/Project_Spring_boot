package com.cts.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import com.cts.entity.User;

import com.cts.service.UserService;

@RequestMapping("/user")
@RestController
public class UserController {

	@Autowired
	UserService userService;

	@PostMapping("/register")
	public ResponseEntity<String> newuser(@RequestBody User user, HttpServletRequest request) {

		// Proceed with user registration

		return ResponseEntity
				.ok(userService.addNewUser(user.getName(), user.getPassword(), user.getEmail(), user.getRole()));

	}

	@PostMapping("/login")
	public ResponseEntity<String> loginUser(@RequestParam String email,
			@RequestParam String password, HttpSession session) {

		if ("okStudent".equals(userService.checkLogin(email, password))) {

			return ResponseEntity.ok("Welcome Student");
		} else if ("okInstructor".equals(userService.checkLogin(email, password))) {

			return ResponseEntity.ok("Welcome Instructor");
		} else if ("user not found".equals(userService.checkLogin(email, password))) {
			return ResponseEntity.ok("You are not a user please Register");
		} else if ("incorrect password".equals(userService.checkLogin(email, password))) {
			return ResponseEntity.ok("incorrect password");
		} else {
			return ResponseEntity.ok("You are not a user please Register");
		}
	}

	public String globalemail = "";

	@PostMapping("/forgotpassword")
	public ResponseEntity<String> forgotpassword(@RequestParam String email) {
		globalemail = email;

		if ("found".equals(userService.forgotPassword(email))) {
			return ResponseEntity.ok("Mail Sent");
		} else {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("User not registered");
		}
	}

	@PostMapping("/resetpassword")
	public ResponseEntity<String> resetpassword(@RequestParam("newPassword") String newpassword) {
		if ("passwordchanged".equals(userService.resetPassword(globalemail, newpassword))) {
			return ResponseEntity.ok("passwordchanged");
		} else {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("password not changed");
		}
	}
	
	@GetMapping("/userId/{id}")
	public User getUserById(@PathVariable int id){
		return userService.getUserById(id);
	}
	
	
}
