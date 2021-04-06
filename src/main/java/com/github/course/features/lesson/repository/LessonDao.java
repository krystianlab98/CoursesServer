package com.github.course.features.lesson.repository;

import com.github.course.features.lesson.model.Lesson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class LessonDao {

    private final LessonRepository lessonRepository;

    @Autowired
    public LessonDao(LessonRepository lessonRepository) {
        this.lessonRepository = lessonRepository;
    }

    public Lesson saveLesson(Lesson lesson) {
        lessonRepository.save(lesson);
        return lesson;
    }

    public List<Lesson> findAllLessons() {
        return lessonRepository.findAll();
    }

    public Optional<Lesson> findLessonById(Long id) {
        return lessonRepository.findById(id);
    }

    public void deleteLesson(Long id) {
        lessonRepository.deleteById(id);
    }
}
