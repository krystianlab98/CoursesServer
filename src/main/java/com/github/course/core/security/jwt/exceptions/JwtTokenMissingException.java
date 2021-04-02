package com.github.course.core.security.jwt.exceptions;


import org.springframework.security.core.AuthenticationException;

public class JwtTokenMissingException extends AuthenticationException {
    private static final long serialVersionUID = 1L;

    public JwtTokenMissingException(String explanation) {
        super(explanation);
    }
}
