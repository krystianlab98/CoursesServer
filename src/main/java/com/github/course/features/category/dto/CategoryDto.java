package com.github.course.features.category.dto;

import com.github.course.features.course.dto.CourseDto;

import java.util.Set;


//@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class CategoryDto {

    private Long id;
    private String title;
    private Set<CourseDto> courses;

    CategoryDto(Long id, String title, Set<CourseDto> courses) {
        this.id = id;
        this.title = title;
        this.courses = courses;
    }

    public static CategoryDtoBuilder builder() {
        return new CategoryDtoBuilder();
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public Set<CourseDto> getCourses() {
        return courses;
    }

    public static class CategoryDtoBuilder {
        private Long id;
        private String title;
        private Set<CourseDto> courses;

        CategoryDtoBuilder() {
        }

        public CategoryDtoBuilder id(Long id) {
            this.id = id;
            return this;
        }

        public CategoryDtoBuilder title(String title) {
            this.title = title;
            return this;
        }

        public CategoryDtoBuilder courses(Set<CourseDto> courses) {
            this.courses = courses;
            return this;
        }

        public CategoryDto build() {
            return new CategoryDto(id, title, courses);
        }

        public String toString() {
            return "CategoryDto.CategoryDtoBuilder(id=" + this.id + ", title=" + this.title + ", courses=" + this.courses + ")";
        }
    }
}
