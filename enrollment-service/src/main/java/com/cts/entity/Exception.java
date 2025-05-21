package com.cts.entity;

import java.time.ZonedDateTime;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Exception {
	
	private String message;
	private ZonedDateTime zonedDateTime;
	private HttpStatus httpstatus;
	@Override
	public String toString() {
		return "Exception [message=" + message + ", zonedDateTime=" + zonedDateTime + ", httpstatus=" + httpstatus
				+ "]";
	}

}
