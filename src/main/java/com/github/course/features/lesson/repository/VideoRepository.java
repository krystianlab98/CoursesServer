package com.github.course.features.lesson.repository;

import com.github.course.features.lesson.model.VideoContent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VideoRepository extends JpaRepository<VideoContent, Long> {
}
