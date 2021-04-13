package com.github.course.features.course.dto;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.github.course.features.lesson.dto.LessonCreateDto;

import java.util.Set;


@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CourseDto {

    Long id;
    String title;
    String description;
    Set<LessonCreateDto> lessons;

    CourseDto(Long id, String title, String description, Set<LessonCreateDto> lessons) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.lessons = lessons;
    }

    public static CourseDtoBuilder builder() {
        return new CourseDtoBuilder();
    }

    public static class CourseDtoBuilder {
        private Long id;
        private String title;
        private String description;
        private Set<LessonCreateDto> lessons;

        CourseDtoBuilder() {
        }

        public CourseDtoBuilder id(Long id) {
            this.id = id;
            return this;
        }

        public CourseDtoBuilder title(String title) {
            this.title = title;
            return this;
        }

        public CourseDtoBuilder description(String description) {
            this.description = description;
            return this;
        }

        public CourseDtoBuilder lessons(Set<LessonCreateDto> lessons) {
            this.lessons = lessons;
            return this;
        }

        public CourseDto build() {
            return new CourseDto(id, title, description, lessons);
        }

        public String toString() {
            return "CourseDto.CourseDtoBuilder(id=" + this.id + ", title=" + this.title + ", description=" + this.description + ", lessons=" + this.lessons + ")";
        }
    }
}
