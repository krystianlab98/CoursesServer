package com.github.course.features.lesson.service;

import com.github.course.features.lesson.dto.LessonCreateDto;
import com.github.course.features.lesson.dto.LessonResponseDto;

import java.util.Set;

public interface LessonService {
    Set<LessonResponseDto> getLessonsByCourse(Long id);

    LessonResponseDto getLessonById(Long id);

    void createLesson(LessonCreateDto lesson, Long courseId);

    void updateLesson(Long id, LessonCreateDto newLesson);

    void deleteLesson(Long id);

    String findPathByLesson(Long fileId);
}
