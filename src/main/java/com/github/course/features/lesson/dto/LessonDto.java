package com.github.course.features.lesson.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.github.course.features.lesson.model.ContentType;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class LessonDto {

    Long id;
    String title;
    String description;
    ContentType contentType;


    public LessonDto(Long id, String title, String description, ContentType contentType) {
        this.contentType = contentType;
        this.description = description;
        this.title = title;
        this.id = id;
    }

    public static LessonDtoBuilder builder() {
        return new LessonDtoBuilder();
    }

    public Long getId() {
        return id;
    }


    public String getTitle() {
        return title;
    }


    public String getDescription() {
        return description;
    }


    public ContentType getContentType() {
        return contentType;
    }

    public static class LessonDtoBuilder {
        private Long id;
        private String title;
        private String description;
        private ContentType contentType;

        LessonDtoBuilder() {
        }

        public LessonDtoBuilder id(Long id) {
            this.id = id;
            return this;
        }

        public LessonDtoBuilder title(String title) {
            this.title = title;
            return this;
        }

        public LessonDtoBuilder description(String description) {
            this.description = description;
            return this;
        }

        public LessonDtoBuilder contentType(ContentType contentType) {
            this.contentType = contentType;
            return this;
        }

        public LessonDto build() {
            return new LessonDto(id, title, description, contentType);
        }

        public String toString() {
            return "LessonDto.LessonDtoBuilder(id=" + this.id + ", title=" + this.title + ", description=" + this.description + ", contentType=" + this.contentType + ")";
        }
    }
}
