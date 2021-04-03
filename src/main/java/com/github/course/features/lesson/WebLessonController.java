package com.github.course.features.lesson;

import com.github.course.features.lesson.dto.LessonDto;
import com.github.course.features.lesson.dto.LessonMapper;
import com.github.course.features.lesson.service.LessonService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

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
    public ResponseEntity<List<LessonDto>> readLessons() {
        try {
            List<LessonDto> lessons = lessonService
                    .getLessons()
                    .stream()
                    .map(LessonMapper::convertLessonToDto)
                    .collect(Collectors.toList());
            return new ResponseEntity<>(lessons, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Exception appear at Get method on /lessons endpoint, error {}", e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<LessonDto> readLessons(@PathVariable Long id) {
        try {
            LessonDto lessonDto = LessonMapper.convertLessonToDto(lessonService.getLessonById(id));
            return new ResponseEntity<>(lessonDto, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Exception appear at Get method on /lessons/{id} endpoint, error {}", e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }

    @PostMapping()
    public ResponseEntity<HttpStatus> createLesson(@RequestBody LessonDto lessonDto) {
        try {
            lessonService.createLesson(LessonMapper.convertDtoToLesson(lessonDto));
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (Exception e) {
            log.error("Exception appear at Post method on /lessons endpoint, error {}", e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<HttpStatus> createLesson(@RequestBody LessonDto lessonDto, @PathVariable Long id) {
        try {
            lessonService.updateLesson(id, LessonMapper.convertDtoToLesson(lessonDto));
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
