package com.bbd.blog.exceptions;

public class InvalidUserCredentialsException extends Exception{
	
	public InvalidUserCredentialsException(String errorMessage) {
        super(errorMessage);
    }
}
