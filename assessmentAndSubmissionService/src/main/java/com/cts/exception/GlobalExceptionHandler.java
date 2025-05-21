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

@RestControllerAdvice
@Hidden
public class GlobalExceptionHandler {
	
	@Autowired
	LoggingClient loggingClient;
	
	@ExceptionHandler(AssessmentNotFoundException.class)
	public ResponseEntity<ErrorMessage> assessmentNotFound(Exception ex){
		ErrorMessage errorMessage = new ErrorMessage(
				ex.getMessage(),
				ZonedDateTime.now(ZoneId.of("Asia/Kolkata")),
				HttpStatus.NOT_FOUND);
		loggingClient.logMessage("WARN",errorMessage.toString());
		return new ResponseEntity<>(errorMessage,HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(SubmissionNotFoundException.class)
	public ResponseEntity<ErrorMessage> submissionNotFound(Exception ex){
		ErrorMessage errorMessage = new ErrorMessage(
				ex.getMessage(),
				ZonedDateTime.now(ZoneId.of("Asia/Kolkata")),
				HttpStatus.NOT_FOUND);
		loggingClient.logMessage("WARN",errorMessage.toString());
		return new ResponseEntity<>(errorMessage,HttpStatus.NOT_FOUND);
	}
	
}
