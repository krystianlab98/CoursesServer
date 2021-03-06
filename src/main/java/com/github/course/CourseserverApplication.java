package com.github.course;

import com.github.course.features.category.Category;
import com.github.course.features.category.CategoryDao;
import com.github.course.features.course.Course;
import com.github.course.features.course.CourseDao;
import com.github.course.features.lesson.model.Lesson;
import com.github.course.features.lesson.model.TextContent;
import com.github.course.features.lesson.repository.LessonDao;
import com.github.course.features.user.model.Role;
import com.github.course.features.user.model.User;
import com.github.course.features.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@SpringBootApplication
public class CourseserverApplication {

    public static void main(String[] args) {
        SpringApplication.run(CourseserverApplication.class, args);

    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public CommandLineRunner insertDbRecords(@Autowired CourseDao courseDao,
                                             @Autowired CategoryDao categoryDao,
                                             @Autowired LessonDao lessonDao,
                                             @Autowired UserService userService) {
        return (args) -> {

            PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

            List<Role> roles = Arrays.asList(Role.ROLE_USER);

            User user = new User();
            user.setUsername("user");
            user.setPassword("user1");
            user.setEmail("user@gmail.com");
            user.setRoles(roles);

            user.setEnabled(true);
            userService.signup(user);


            Category category = new Category();
            category.setTitle("backend programing");
            Category category2 = new Category();
            category2.setTitle("frontend programing");

            Course course = new Course(category);
            course.setTitle("Java Course");
            course.setDescription("Complete course for java dev");
            Course course2 = new Course(category);
            course2.setTitle("Python Course");
            course2.setDescription("Complete course for python developers");
            Course course3 = new Course(category2);
            course3.setTitle("JavaScript Course");
            course3.setDescription("Complete course for fronted devs ");


            Set<Course> courses = new HashSet<>();
            courses.add(course);
            courses.add(course2);
            category.setCourses(courses);

            Set<Course> courses2 = new HashSet<>();
            courses2.add(course3);
            category2.setCourses(courses2);

//            course.setCategoryId(category.getId());
//            course2.setCategoryId(category.getId());
//            course3.setCategoryId(category2.getId());

//            courseDao.save(course);
//            courseDao.save(course2);
//            courseDao.save(course3);

            categoryDao.save(category);
            categoryDao.save(category2);


            TextContent textContent = new TextContent();
            textContent.setText("txt");
            TextContent textContent2 = new TextContent();
            textContent2.setText("txt2");

            Lesson lesson1 = new Lesson();
            lesson1.setTitle("pierwsza lekcja");
            lesson1.setDescription("pierwsza lekcja z programowania");
            lesson1.setContentType(textContent);
            //lesson1.setCourse(course);

            Lesson lesson2 = new Lesson();
            lesson2.setTitle("2 lekcja");
            lesson2.setDescription("2 lekcja z programowania");
            lesson2.setContentType(textContent2);


            lessonDao.saveLesson(lesson1);
            lessonDao.saveLesson(lesson2);


        };

    }
}
