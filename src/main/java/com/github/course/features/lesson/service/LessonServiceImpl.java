package com.github.course.features.lesson.service;

import com.github.course.features.course.Course;
import com.github.course.features.course.CourseDao;
import com.github.course.features.file.FileService;
import com.github.course.features.lesson.dto.LessonCreateDto;
import com.github.course.features.lesson.dto.LessonResponseDto;
import com.github.course.features.lesson.model.Lesson;
import com.github.course.features.lesson.model.TextContent;
import com.github.course.features.lesson.model.VideoContent;
import com.github.course.features.lesson.repository.LessonDao;
import com.github.course.features.lesson.repository.LessonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LessonServiceImpl implements LessonService {

    CourseDao courseDao;
    LessonDao lessonDao;
    LessonRepository lessonRepository;
    FileService fileService;

    @Autowired
    public LessonServiceImpl(LessonDao lessonDao, LessonRepository lessonRepository, FileService fileService, CourseDao courseDao) {
        this.lessonDao = lessonDao;
        this.lessonRepository = lessonRepository;
        this.fileService = fileService;
        this.courseDao = courseDao;
    }

    @Override
    public List<Lesson> getLessons() {
        return lessonDao.findAllLessons();
    }

    @Override
    public Lesson getLessonById(Long id) {
        return lessonDao.findLessonById(id).get();
    }

    @Override
    public void createLesson(LessonCreateDto lessonDto, Long courseId) {
        Course course = courseDao.findCourseById(courseId).orElseThrow();
        Lesson lesson = new Lesson();

        if (lessonDto.getContentType().equals("video/mp4")) {

            VideoContent videoContent = new VideoContent();
            String path = this.getPathByCourse(course, lessonDto);
            //String pathWithFileName = path + "/" + lessonDto.getTitle().toString();
            String storedPath = fileService.storeFile(lessonDto.getVideoFile(), path);
            videoContent.setPath(storedPath);
            videoContent.setFileName(fileService.getName(lessonDto.getVideoFile()));
            String url = fileService.getUrlByPath(videoContent.getFileName());
            videoContent.setUrl(url);
            videoContent.setContentType(lessonDto.getVideoFile().getContentType());
            lesson.setContentType(videoContent);

        } else if (lessonDto.getContentType().equals("text")) {

            TextContent textContent = new TextContent();
            textContent.setText(lessonDto.getText());
            lesson.setContentType(textContent);

        }
        lesson.setTitle(lessonDto.getTitle());
        lesson.setDescription(lessonDto.getDescription());

        lessonDao.saveLesson(lesson);
        course.addLesson(lesson);
        courseDao.save(course);
    }

    @Override
    public void updateLesson(Long id, Lesson newLesson) {
        lessonRepository.findById(id)
                .map(lesson -> {
                    lesson.setTitle(newLesson.getTitle());
                    lesson.setDescription(newLesson.getDescription());
                    return lessonRepository.save(lesson);
                }).orElseThrow();
    }

    @Override
    public void deleteLesson(Long id) {
        lessonDao.deleteLesson(id);
    }


}
