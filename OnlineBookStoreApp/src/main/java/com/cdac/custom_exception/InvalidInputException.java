package com.cdac.custom_exception;

@SuppressWarnings("serial")
public class InvalidInputException extends RuntimeException{

	public InvalidInputException(String mesg) {
		super(mesg);
	}
}
