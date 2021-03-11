package com.github.course.features.category;

import com.github.course.features.course.Course;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Entity
@Getter
@Setter
@Table(name = "categories")
public class Category {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(name = "title")
    private String title;

    @OneToMany(targetEntity = Course.class, cascade = CascadeType.PERSIST,
            fetch = FetchType.EAGER, orphanRemoval = true)
    public Set<Course> courses;

    public Category() {
    }

    public Category(String title) {
        this.title = title;
    }

    public void replaceCourses(Set<Course> newCourses) {
        this.courses.removeAll(courses);
        this.courses.addAll(newCourses);
    }

}
