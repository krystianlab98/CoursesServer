package com.github.course.features.lesson.model;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Any;
import org.hibernate.annotations.AnyMetaDef;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.MetaValue;

import javax.persistence.*;

@Entity
@Getter
@Setter
public class Lesson {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column
    private String title;

    @Column
    private String description;

    @Any(
            metaColumn = @Column(name = "content_type", length = 3),
            fetch = FetchType.EAGER
    )
    @AnyMetaDef(
            idType = "long", metaType = "string",
            metaValues = {
                    @MetaValue(targetEntity = TextContent.class, value = "TXT"),
                    @MetaValue(targetEntity = VideoContent.class, value = "VID")
            }
    )
    @Cascade({org.hibernate.annotations.CascadeType.ALL})
    @JoinColumn(name = "type_id")
    private ContentType contentType;



    public Lesson(String title, String description, ContentType contentType) {
        this.title = title;
        this.description = description;
        this.contentType = contentType;
    }

    public Lesson() {
    }

    public void setContentType(ContentType contentType) {
        this.contentType = contentType;
    }
}
