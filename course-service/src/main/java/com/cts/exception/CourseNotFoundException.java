package com.cts.exception;

public class CourseNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 6344038423732977490L;

	public CourseNotFoundException() {
		super();
	}

	public CourseNotFoundException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public CourseNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}

	public CourseNotFoundException(String message) {
		super(message);
	}

	public CourseNotFoundException(Throwable cause) {
		super(cause);
	}

	
}
