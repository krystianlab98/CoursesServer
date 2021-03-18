package com.github.course.features.category.dto;

import com.github.course.features.category.Category;
import com.github.course.features.course.dto.CourseMapper;

import java.util.stream.Collectors;

public class CategoryMapper {

    public static Category convertDtoToCategory(CategoryDto dto) {
        Category category = new Category();
        category.setId(dto.getId());
        category.setTitle(dto.getTitle());
        category.setCourses(dto.getCourses().stream().map(CourseMapper::convertDtoToCourse).collect(Collectors.toSet()));
        return category;
    }

    public static CategoryDto convertCategoryToDto(Category category) {
        CategoryDto categoryDto = CategoryDto.builder()
                .id(category.getId())
                .title(category.getTitle())
                .courses(category.getCourses().stream().map(CourseMapper::convertCourseToDto).collect(Collectors.toSet()))
                .build();
        return categoryDto;
    }
}
