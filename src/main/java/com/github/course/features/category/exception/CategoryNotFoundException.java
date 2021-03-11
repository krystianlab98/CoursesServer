package com.github.course.features.category.exception;

public class CategoryNotFoundException extends RuntimeException {
    public CategoryNotFoundException(Long id) {
        super("Could not find Category: " + id);
    }
}
