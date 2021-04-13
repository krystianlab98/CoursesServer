package com.github.course.features.lesson.service;

import com.github.course.features.lesson.dto.LessonCreateDto;
import com.github.course.features.lesson.dto.LessonResponseDto;

import java.util.Set;

public interface LessonService {
    List<Lesson> getLessons();

    Lesson getLessonById(Long id);

    void createLesson(LessonCreateDto lesson, Long courseId);

    void updateLesson(Long id, Lesson lesson);

    void deleteLesson(Long id);
}
