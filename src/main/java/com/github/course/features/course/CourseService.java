package com.github.course.features.course;

import java.util.List;

public interface CourseService {

    List<Course> getCourses();

    Course getCourseById(long id);

    void create(Course course);

    void update(Course course, Long id);

    void delete(Long id);
}
