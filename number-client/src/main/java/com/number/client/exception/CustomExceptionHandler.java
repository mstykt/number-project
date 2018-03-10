package com.number.client.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler(NumberException.class)
	public ResponseEntity<ErrorResponse> exceptionHandler(NumberException ex) {
		ErrorResponse errorResponse = new ErrorResponse();
		errorResponse.setErrorCode(ex.getHttpStatus().value());
		errorResponse.setHttpStatus(ex.getHttpStatus());
		errorResponse.setMessage(ex.getMessage());
		errorResponse.setTimestamp(ex.getTimestamp());

		return new ResponseEntity<>(errorResponse, ex.getHttpStatus());
	}
}
