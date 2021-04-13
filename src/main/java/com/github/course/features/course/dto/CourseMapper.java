package com.github.course.features.course.dto;

import com.github.course.features.course.Course;

public class CourseMapper {

    public static Course convertDtoToCourse(CourseDto courseDto) {

        Course course = new Course();
        course.setId(courseDto.id);
        course.setTitle(courseDto.title);
        course.setDescription(courseDto.description);
        //course.setLessons(courseDto.lessons.stream().map(LessonMapper::convertDtoToLesson).collect(Collectors.toSet()));
        return course;
    }

    public static CourseDto convertCourseToDto(Course course) {
        CourseDto courseDto = CourseDto.builder()
                .id(course.getId())
                .title(course.getTitle())
                .description(course.getDescription())
                //.lessons(course.getLessons().stream().map(LessonMapper::convertLessonToDto).collect(Collectors.toSet()))
                .build();
        return courseDto;
    }
}
