package com.number.client.exception;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;

public class NumberException extends Exception {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private HttpStatus httpStatus;
	private String errorMessage;
	private LocalDateTime timestamp;
	
	public NumberException() {
		super();
	}

	public NumberException(HttpStatus httpStatus, String errorMessage) {
		super(errorMessage);
		this.httpStatus = httpStatus;
		this.errorMessage = errorMessage;
	}

	public HttpStatus getHttpStatus() {
		return httpStatus;
	}

	public void setHttpStatus(HttpStatus httpStatus) {
		this.httpStatus = httpStatus;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public LocalDateTime getTimestamp() {
		timestamp = LocalDateTime.now();
		return timestamp;
	}

	public void setTimestamp(LocalDateTime timestamp) {
		this.timestamp = timestamp;
	}

}
