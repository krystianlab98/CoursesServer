package com.github.course.features.lesson;

import com.github.course.features.lesson.dto.LessonCreateDto;
import com.github.course.features.lesson.dto.LessonResponseDto;
import com.github.course.features.lesson.service.LessonService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@Slf4j
@RestController
@RequestMapping("web/api")
public class WebLessonController {

    LessonService lessonService;

    @Autowired
    public WebLessonController(LessonService lessonService) {
        this.lessonService = lessonService;
    }

    @GetMapping("/courses/{id}/lessons")
    public ResponseEntity<Set<LessonResponseDto>> readAllLessons(@PathVariable("id") Long id) {
        try {
            Set<LessonResponseDto> lessons = lessonService.getLessonsByCourse(id);
            return new ResponseEntity<>(lessons, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Exception appear at Get method on /lessons endpoint, error {}", e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }

    @GetMapping("/lessons/{id}")
    public ResponseEntity<LessonCreateDto> readLessons(@PathVariable Long id) {
        try {
            LessonCreateDto lessonCreateDto; //= LessonMapper.convertLessonToDto(lessonService.getLessonById(id));
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            log.error("Exception appear at Get method on /lessons/{id} endpoint, error {}", e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }

    @PostMapping("/courses/{id}/lessons")
    public ResponseEntity<HttpStatus> createLesson(@PathVariable("id") Long id, @ModelAttribute LessonCreateDto lessonCreateDto) {
        try {
            lessonService.createLesson(lessonCreateDto, id);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (Exception e) {
            log.error("Exception appear at Post method on /lessons endpoint, error {}", e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }

    @PutMapping("/lessons/{id}")
    public ResponseEntity<HttpStatus> createLesson(@RequestBody LessonCreateDto lessonCreateDto, @PathVariable Long id) {
        try {
            lessonService.updateLesson(id, lessonCreateDto);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            log.error("Exception appear at Put method on /lessons endpoint, error {}", e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }

    @DeleteMapping("/lessons/{id}")
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
