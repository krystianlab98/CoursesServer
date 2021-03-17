package com.github.course.features.course;

import com.github.course.features.course.dto.CourseDto;
import com.github.course.features.course.dto.CourseMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping(path = "web/api")
public class WebCourseController {

    private final CourseService courseService;

    @Autowired
    public WebCourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    @GetMapping("/courses")
    public ResponseEntity<List<CourseDto>> readCourses() {
        try {
            List<CourseDto> courses = courseService.getCourses().stream().map(CourseMapper::convertCourseToDto).collect(Collectors.toList());
            return new ResponseEntity<>(courses, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Exception appear at Get method on /courses endpoint, error {}", e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }

    @GetMapping("/courses/{id}")
    public ResponseEntity<CourseDto> readCourseById(@PathVariable Long id) {
        try {
            CourseDto course = CourseMapper.convertCourseToDto(courseService.getCourseById(id));
            return new ResponseEntity<>(course, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Exception appear at Get method on /courses/id endpoint, error {}", e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }

    @PostMapping("/courses")
    public ResponseEntity<HttpStatus> createCourse(@RequestBody CourseDto course) {
        try {
            courseService.create(CourseMapper.convertDtoToCourse(course));
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (Exception e) {
            log.error("Exception appear at POST method on /courses endpoint, error {}", e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }

    @PutMapping("/courses/{id}")
    public ResponseEntity<HttpStatus> updateCourse(@PathVariable Long id, @RequestBody CourseDto course) {
        try {
            courseService.update(CourseMapper.convertDtoToCourse(course), id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            log.error("error occurred in PUT method /courses endpoint, error {}", e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }

    @DeleteMapping("courses/{id}")
    public ResponseEntity<HttpStatus> deleteCourse(@PathVariable Long id) {
        try {
            courseService.delete(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (EmptyResultDataAccessException e) {
            log.error("error occurred in DELETE method /courses endpoint, error {}", e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }
}
