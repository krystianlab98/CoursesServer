package com.github.course.features.course;

import com.github.course.features.lesson.Lesson;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;


@Getter
@Setter
@Entity
@Table(name = "courses")
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "categoryId")
    private Long categoryId;

    @Column(name = "title")
    private String title;

    @Column
    private String description;

    @OneToMany(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "courseId", updatable = false, insertable = false)
    public Set<Lesson> lessons;


    public Course(Long id, String title, String description) {
        this.id = id;
        this.title = title;
        this.description = description;
    }

    public Course() {
    }

}

