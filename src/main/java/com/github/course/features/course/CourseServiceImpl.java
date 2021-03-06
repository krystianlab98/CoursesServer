package com.github.course.features.course;

import com.github.course.features.category.CategoryDao;
import com.github.course.features.course.exception.CourseNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseServiceImpl implements CourseService {

    CourseDao courseDao;
    CourseRepository courseRepository;
    CategoryDao categoryDao;

    @Autowired
    public CourseServiceImpl(CourseDao courseDao, CourseRepository courseRepository, CategoryDao categoryDao) {
        this.courseDao = courseDao;
        this.courseRepository = courseRepository;
        this.categoryDao = categoryDao;
    }

    @Override
    public List<Course> getCourses() {
        return courseDao.findAllCourses();
    }

    @Override
    public Course getCourseById(long id) {
        return courseDao.findCourseById(id).orElseThrow(() -> new CourseNotFoundException(id));
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
                }).orElseThrow(() -> new CourseNotFoundException(id));
    }

    @Override
    public void delete(Long id) {
        courseDao.deleteById(id);
    }
}
