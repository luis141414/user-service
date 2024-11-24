package com.defi.userservice.application.exception;

public class InvalidCredentialsException extends RuntimeException {
    public InvalidCredentialsException(String message) {
    	super(message);
    }
}