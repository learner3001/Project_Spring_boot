package com.cts.entity;

import java.time.ZonedDateTime;
import org.springframework.http.HttpStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

@AllArgsConstructor
@Data
@ToString
public class ErrorMessage {
	private ZonedDateTime zonedDateTime;
	private String message;
	private HttpStatus httpStatus;
}
