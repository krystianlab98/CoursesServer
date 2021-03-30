package com.github.course.features.user.exceptions;

public class InvalidUserCredentialsException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public InvalidUserCredentialsException(String msg) {
        super(msg);
    }
}