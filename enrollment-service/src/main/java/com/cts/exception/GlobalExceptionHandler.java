package com.cts.exception;

import java.time.ZoneId;
import java.time.ZonedDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.cts.client.LoggingClient;
import com.cts.entity.Exception;

import io.swagger.v3.oas.annotations.Hidden;

@Hidden
@RestControllerAdvice
public class GlobalExceptionHandler {
	
	@Autowired
	LoggingClient loggingClient;
	
	@ExceptionHandler(EnrollmentAlreadyExistException.class)
	public ResponseEntity<Object> enrollmentAlreadyExistException(EnrollmentAlreadyExistException ex){
		Exception exception= new Exception(ex.getMessage(),ZonedDateTime.now(ZoneId.of("Asia/Kolkata")),HttpStatus.CONFLICT);
		loggingClient.logMessage("WARN",exception.toString());
		return new ResponseEntity<>(exception,HttpStatus.CONFLICT);
	}
	
	@ExceptionHandler(EnrollmentDoesnotExistException.class)
	public ResponseEntity<Object> enrollmentDoesnotExistException(EnrollmentDoesnotExistException ex){
		Exception exception= new Exception(ex.getMessage(),ZonedDateTime.now(ZoneId.of("Asia/Kolkata")),HttpStatus.NOT_FOUND);
		loggingClient.logMessage("WARN",exception.toString());
		return new ResponseEntity<>(exception,HttpStatus.NOT_FOUND);
	}	
	

}
