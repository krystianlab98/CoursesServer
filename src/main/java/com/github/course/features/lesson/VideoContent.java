package com.github.course.features.lesson;

import javax.persistence.*;

@Entity
@DiscriminatorValue("VID")
@Table(name = "video_content")
public class VideoContent implements ContentType {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column
    private String url;

    public VideoContent() {
    }

    public String getUrl() {
        return this.url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
