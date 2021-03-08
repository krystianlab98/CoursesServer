package com.github.course.features.course;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseServiceImpl implements CourseService {

    CourseDao courseDao;
    CourseRepository courseRepository;

    @Autowired
    public CourseServiceImpl(CourseDao courseDao, CourseRepository courseRepository) {
        this.courseDao = courseDao;
        this.courseRepository = courseRepository;
    }

    @Override
    public List<Course> getCourses() {
        return courseDao.findAllCourses();
    }

    @Override
    public Course getCourseById(long id) {
        return courseDao.findCourseById(id).get();
    }

    @Override
    public void create(Course course) {
        courseDao.save(course);
    }

    @Override
    public void update(Course newCourse, Long id) {
        courseRepository.findById(id)
                .map(course -> {
                    course.setTitle(newCourse.getTitle());
                    course.setDescription(newCourse.getDescription());
                    return courseRepository.save(course);
                }).orElseThrow();
    }

    @Override
    public void delete(Long id) {
        Course course = courseDao.findCourseById(id).get();
        courseDao.delete(course);
    }
}
