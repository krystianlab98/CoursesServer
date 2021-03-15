package com.github.course.features.lesson;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("web/api/lessons")
public class WebLessonController {

    LessonService lessonService;

    @Autowired
    public WebLessonController(LessonService lessonService) {
        this.lessonService = lessonService;
    }

    @GetMapping
    public ResponseEntity<List<Lesson>> readLessons() {
        try {
            List<Lesson> lessons = lessonService.getLessons();
            return new ResponseEntity<>(lessons, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Exception appear at Get method on /lessons endpoint, error {}", e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Lesson> readLessons(@PathVariable Long id) {
        try {
            Lesson lesson = lessonService.getLessonById(id);
            return new ResponseEntity<>(lesson, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Exception appear at Get method on /lessons/{id} endpoint, error {}", e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }

    @PostMapping()
    public ResponseEntity<HttpStatus> createLesson(@RequestBody Lesson lesson) {
        try {
            lessonService.createLesson(lesson);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (Exception e) {
            log.error("Exception appear at Post method on /lessons endpoint, error {}", e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<HttpStatus> createLesson(@RequestBody Lesson lesson, @PathVariable Long id) {
        try {
            lessonService.updateLesson(id, lesson);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            log.error("Exception appear at Put method on /lessons endpoint, error {}", e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteLesson(@PathVariable Long id) {
        try {
            lessonService.deleteLesson(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            log.error("Exception appear at Put method on /lessons endpoint, error {}", e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }


}
