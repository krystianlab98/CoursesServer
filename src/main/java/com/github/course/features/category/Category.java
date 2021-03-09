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

    @OneToMany(targetEntity = Course.class, cascade = CascadeType.MERGE,
            fetch = FetchType.EAGER, orphanRemoval = true)
    public Set<Course> courses;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(name = "title")
    private String title;

    public Category() {
    }

    public Category(String title) {
        this.title = title;
    }

}
