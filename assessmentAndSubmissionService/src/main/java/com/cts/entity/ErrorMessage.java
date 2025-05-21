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
	private String message;
	private ZonedDateTime zonedDateTime;
	private HttpStatus httpstatus;
}
