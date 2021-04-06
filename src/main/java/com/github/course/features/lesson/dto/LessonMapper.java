package com.github.course.features.lesson.dto;

import com.github.course.features.lesson.model.Lesson;

public class LessonMapper {

    public static Lesson convertDtoToLesson(LessonDto dto) {
        Lesson lesson = new Lesson();
        lesson.setId(dto.id);
        lesson.setTitle(dto.title);
        lesson.setDescription(dto.description);
        lesson.setContentType(dto.contentType);
        return lesson;
    }

    public static LessonDto convertLessonToDto(Lesson lesson) {
        LessonDto lessonDto = LessonDto.builder()
                .title(lesson.getTitle())
                .description(lesson.getDescription())
                .contentType(lesson.getContentType())
                .id(lesson.getId())
                .build();
        return lessonDto;
    }
}
