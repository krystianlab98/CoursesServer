package com.github.course.features.lesson;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LessonServiceImpl implements LessonService {

    LessonDao lessonDao;
    LessonRepository lessonRepository;

    @Autowired
    public LessonServiceImpl(LessonDao lessonDao, LessonRepository lessonRepository) {
        this.lessonDao = lessonDao;
        this.lessonRepository = lessonRepository;
    }

    @Override
    public List<Lesson> getLessons() {
        return lessonDao.findAllLessons();
    }

    @Override
    public Lesson getLessonById(Long id) {
        return lessonDao.findLessonById(id).get();
    }

    @Override
    public void createLesson(Lesson lesson) {
        lessonDao.saveLesson(lesson);
    }

    @Override
    public void updateLesson(Long id, Lesson newLesson) {
        lessonRepository.findById(id)
                .map(lesson -> {
                    lesson.setTitle(newLesson.getTitle());
                    lesson.setDescription(newLesson.getDescription());
                    lesson.setContentType(newLesson.getContentType());
                    return lessonRepository.save(lesson);
                }).orElseThrow();
    }

    @Override
    public void deleteLesson(Long id) {
        lessonDao.deleteLesson(id);
    }


}
