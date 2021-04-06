package com.github.course.features.lesson.service;

import com.github.course.features.lesson.model.Lesson;

import java.util.List;

public interface LessonService {
    List<Lesson> getLessons();

    Lesson getLessonById(Long id);

    void createLesson(Lesson lesson);

    void updateLesson(Long id, Lesson lesson);

    void deleteLesson(Long id);
}