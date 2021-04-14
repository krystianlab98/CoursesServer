package com.github.course.features.lesson.repository;

import com.github.course.features.lesson.model.VideoContent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class VideoDao {

    VideoRepository videoRepository;

    @Autowired
    public VideoDao(VideoRepository videoRepository) {
        this.videoRepository = videoRepository;
    }

    public Optional<VideoContent> findVideoById(Long id) {
        return videoRepository.findById(id);
    }

    public void save(VideoContent videoContent) {
        videoRepository.save(videoContent);
    }
}
