package com.cts.exception;


public class EnrollmentDoesnotExistException extends RuntimeException{

	private static final long serialVersionUID = -381405298147368942L;

	public EnrollmentDoesnotExistException(String message) {
		super(message);
	}

}
