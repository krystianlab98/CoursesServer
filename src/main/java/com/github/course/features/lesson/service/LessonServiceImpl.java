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
import com.github.course.features.lesson.repository.VideoDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class LessonServiceImpl implements LessonService {

    CourseDao courseDao;
    LessonDao lessonDao;
    LessonRepository lessonRepository;
    FileService fileService;
    VideoDao videoDao;

    @Autowired
    public LessonServiceImpl(LessonDao lessonDao, LessonRepository lessonRepository, FileService fileService, CourseDao courseDao, VideoDao videoDao) {
        this.lessonDao = lessonDao;
        this.lessonRepository = lessonRepository;
        this.fileService = fileService;
        this.courseDao = courseDao;
        this.videoDao = videoDao;
    }

    @Override
    public Set<LessonResponseDto> getLessonsByCourse(Long id) {

        Course course = courseDao.findCourseById(id).orElseThrow();
        Set<Lesson> lessons = course.getLessons();

        Set<LessonResponseDto> lessonsResponse = lessons.stream()
                .map(lesson -> {
                    final LessonResponseDto dto = new LessonResponseDto();
                    dto.setId(lesson.getId());
                    dto.setDescription(lesson.getDescription());
                    dto.setTitle(lesson.getTitle());
                    dto.setContentType(lesson.getContentType());
                    return dto;
                })
                .collect(Collectors.toSet());

        return lessonsResponse;
    }

    @Override
    public LessonResponseDto getLessonById(Long id) {

        Lesson lesson = lessonDao.findLessonById(id).get();
        LessonResponseDto dto = new LessonResponseDto();
        dto.setId(lesson.getId());
        dto.setTitle(lesson.getTitle());
        dto.setDescription(lesson.getTitle());
        dto.setContentType(lesson.getContentType());

        return dto;
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
            videoContent.setPath(path);
            videoContent.setFileName(fileService.getName(lessonDto.getVideoFile()));
            videoDao.save(videoContent);
            String url = fileService.getUrlByPath(videoContent.getId().toString(), videoContent.getFileName());
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
    public void updateLesson(Long id, LessonCreateDto newLesson) {
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

    private String getPathByCourse(Course course, LessonCreateDto lessonDto) {
        String path = course.getCategory().getTitle().toString()
                + "/" + course.getTitle().toString() + "/" + lessonDto.getTitle().toString();
        return path.replaceAll(" ", "");

    }

    @Override
    public String findPathByLesson(Long fileId) {
        VideoContent video = videoDao.findVideoById(fileId).orElseThrow();
        return video.getPath();
    }


}
