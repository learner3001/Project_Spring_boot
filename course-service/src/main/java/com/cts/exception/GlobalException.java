package com.cts.exception;

import java.time.ZoneId;
import java.time.ZonedDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.cts.client.LoggingClient;
import com.cts.entity.ErrorMessage;

import io.swagger.v3.oas.annotations.Hidden;

@Hidden
@RestControllerAdvice
public class GlobalException {

	@Autowired
	LoggingClient loggingClient;

	@ExceptionHandler(CourseAlreadyExistsException.class)
	public ResponseEntity<ErrorMessage> courseAlreadyExists(CourseAlreadyExistsException ex) {
		ErrorMessage errorMessage = new ErrorMessage(ZonedDateTime.now(ZoneId.of("Asia/Kolkata")), ex.getMessage(), HttpStatus.CONFLICT);
		loggingClient.logMessage("WARN", errorMessage.toString());
		return new ResponseEntity<>(
				errorMessage,
				HttpStatus.CONFLICT);
	}

	@ExceptionHandler(CourseNotFoundException.class)
	public ResponseEntity<ErrorMessage> courseNotFound(CourseNotFoundException ex) {
		ErrorMessage errorMessage = new ErrorMessage(ZonedDateTime.now(ZoneId.of("Asia/Kolkata")), ex.getMessage(), HttpStatus.CONFLICT);
		loggingClient.logMessage("WARN", errorMessage.toString());
		return new ResponseEntity<>(
				errorMessage,
				HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(UnknownException.class)
	public ResponseEntity<ErrorMessage> unknown(CourseNotFoundException ex) {
		ErrorMessage errorMessage = new ErrorMessage(ZonedDateTime.now(ZoneId.of("Asia/Kolkata")), ex.getMessage(), HttpStatus.CONFLICT);
		loggingClient.logMessage("WARN", errorMessage.toString());
		return new ResponseEntity<>(
				errorMessage,
				HttpStatus.NOT_FOUND);
	}

}
