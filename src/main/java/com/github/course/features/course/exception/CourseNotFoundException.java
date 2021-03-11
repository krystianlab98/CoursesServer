package com.github.course.features.course.exception;

public class CourseNotFoundException extends RuntimeException {
    public CourseNotFoundException(Long id) {
        super("Could not find Course: " + id);
    }
}
