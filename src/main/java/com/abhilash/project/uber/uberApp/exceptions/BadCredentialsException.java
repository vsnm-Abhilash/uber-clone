package com.abhilash.project.uber.uberApp.exceptions;

public class BadCredentialsException extends RuntimeException {
    public BadCredentialsException(String message) {
        super(message);
    }

    public BadCredentialsException() {
    }
}
