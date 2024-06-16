package com.bookstore.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.HashMap;

@SuppressWarnings({"unchecked", "rawtypes"})
@ControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {


	@ExceptionHandler(Exception.class)
	public final ResponseEntity<Object> handleAllExceptions(Exception ex, WebRequest request) {
		HashMap<String, Object> details = new HashMap<>();
		details.put("statusMessage", ex.getMessage());
		return new ResponseEntity<>(details, HttpStatus.OK);
	}	
}
