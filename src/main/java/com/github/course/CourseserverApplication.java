package com.github.course;

import com.github.course.features.course.Course;
import com.github.course.features.course.CourseDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class CourseserverApplication {

    public static void main(String[] args) {
        SpringApplication.run(CourseserverApplication.class, args);

    }

    @Bean
    public CommandLineRunner insertDbRecords(@Autowired CourseDao courseDao) {
        return (args) -> {
            Course course = new Course();
            course.setTitle("Java Course");
            course.setDescription("Complete course for java dev");
            courseDao.save(course);

            Course course2 = new Course();
            course2.setTitle("Python Course");
            course2.setDescription("Complete course for python developers");
            courseDao.save(course2);
        };

    }
}
