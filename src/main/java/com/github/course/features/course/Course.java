package com.github.course.features.course;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.github.course.features.category.Category;
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

    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @JsonManagedReference
    public Set<Lesson> lessons;

    @Column(name = "title")
    private String title;

    @Column
    private String description;
    @ManyToOne
    @JsonBackReference
    private Category category;


    public Course(String title, String description) {
        this.title = title;
        this.description = description;
    }

    public Course(Category category) {
        this.category = category;
    }

    public Course() {
    }

    public void updateLessons(Set<Lesson> newLessons) {
        this.lessons.clear();
        this.lessons.addAll(newLessons);
    }
}

