package com.github.course.features.user.exceptions;

public class DisabledUserException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public DisabledUserException(String msg) {
        super(msg);
    }
}