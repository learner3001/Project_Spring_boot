package com.cts.exception;

import java.time.ZoneId;
import java.time.ZonedDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.cts.client.LoggingClient;

import io.swagger.v3.oas.annotations.Hidden;

@Hidden
@ControllerAdvice
public class GlobalExceptionHandler {

	@Autowired
	private LoggingClient loggingClient;
	
	@ExceptionHandler(value = {UserAlreadyExistsException.class})
	public ResponseEntity<Object> handleUserAlreadyExistsException(UserAlreadyExistsException e)
	{
		Exception exception = new Exception(e.getMessage(),HttpStatus.CONFLICT,ZonedDateTime.now(ZoneId.of("Asia/Kolkata"))); 
		loggingClient.logMessage("WARN", exception.toString());
		return new ResponseEntity<>(exception,HttpStatus.CONFLICT);
	}
}
