package com.github.course;

import com.github.course.features.category.Category;
import com.github.course.features.category.CategoryDao;
import com.github.course.features.course.Course;
import com.github.course.features.course.CourseDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.HashSet;
import java.util.Set;

@SpringBootApplication
public class CourseserverApplication {

    public static void main(String[] args) {
        SpringApplication.run(CourseserverApplication.class, args);

    }

    @Bean
    public CommandLineRunner insertDbRecords(@Autowired CourseDao courseDao, @Autowired CategoryDao categoryDao) {
        return (args) -> {

            Course course = new Course();
            course.setTitle("Java Course");
            course.setDescription("Complete course for java dev");
            Course course2 = new Course();
            course2.setTitle("Python Course");
            course2.setDescription("Complete course for python developers");
            Course course3 = new Course();
            course3.setTitle("JavaScript Course");
            course3.setDescription("Complete course for fronted devs ");

            Category category = new Category();
            category.setTitle("backend programing");
            Category category2 = new Category();
            category2.setTitle("frontend programing");

            Set<Course> courses = new HashSet<>();
            courses.add(course);
            courses.add(course2);
            category.setCourses(courses);

            Set<Course> courses2 = new HashSet<>();
            courses2.add(course3);
            category2.setCourses(courses2);

            categoryDao.save(category);
            categoryDao.save(category2);

            course.setCategoryId(category.getId());
            course2.setCategoryId(category.getId());
            course3.setCategoryId(category2.getId());


            courseDao.save(course);
            courseDao.save(course2);
            courseDao.save(course3);


        };

    }
}
