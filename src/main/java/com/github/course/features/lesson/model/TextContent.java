package com.github.course.features.lesson.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Setter
@Getter
@DiscriminatorValue("txt")
@Table(name = "text_content")
public class TextContent implements ContentType {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column
    private String content;

    public TextContent(String content) {
        this.content = content;
    }

    public TextContent() {
    }
}
