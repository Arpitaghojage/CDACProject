package com.cdac.exception_handler;
 
import java.util.Map;
import java.util.stream.Collectors;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.cdac.custom_exception.InvalidInputException;
import com.cdac.custom_exception.ResourceNotFoundException;
import com.cdac.dto.ApiResponse;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<?> handleResourceNotFoundException(ResourceNotFoundException e) {
		System.out.println("in catch -invalid input exc");
		return ResponseEntity.status(HttpStatus.BAD_REQUEST)// SC 400
				.body(new ApiResponse(e.getMessage()));
	}

	// add exception handling method - to handle bad request - invalid input
	@ExceptionHandler(InvalidInputException.class)
	public ResponseEntity<?> handleInvalidInputException(InvalidInputException e) {
		System.out.println("in catch -invalid input exc");
		return ResponseEntity.status(HttpStatus.BAD_REQUEST)// SC 400
				.body(new ApiResponse(e.getMessage()));
	}

	// add exception handling method - to catch remaining excs (catch-all)
	@ExceptionHandler(RuntimeException.class)
	public ResponseEntity<?> handleInvalidInputException(RuntimeException e) {
		System.out.println("in catch all");
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)// SC 500
				.body(new ApiResponse(e.getMessage()));
	}

	// add exception handling method - to handle method arg not valid exc
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<?> handleInvalidInputException(MethodArgumentNotValidException e) {
		System.out.println("in catch -MethodArgumentNotValidException");
		Map<String, String> errorMap = e.getFieldErrors().stream()
				.collect(Collectors.toMap(FieldError::getField, FieldError::getDefaultMessage));
		return ResponseEntity.status(HttpStatus.BAD_REQUEST)// SC 400
				.body(errorMap);
	}

	// add exception handling method - to handle ConstraintViolationExce
	@ExceptionHandler(ConstraintViolationException.class)
	public ResponseEntity<?> handleConstraintViolationException(ConstraintViolationException e) {
		System.out.println("in catch - ConstraintViolationException");
		return ResponseEntity.status(HttpStatus.BAD_REQUEST)// SC 400
				.body(new ApiResponse(e.getMessage()));
	}
}
