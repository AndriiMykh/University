package org.foxminded.university.mapper;

import org.foxminded.university.dto.LessonDto;
import org.foxminded.university.entity.Lesson;

public class LessonMapper {
    public static Lesson lessonDtoToLesson(LessonDto lessonDto){
        if (lessonDto != null){
            Lesson lesson = Lesson.builder()
                    .withId(lessonDto.getId())
                    .withDescription(lessonDto.getDescription())
                    .withName(lessonDto.getName())
                    .build();
            return lesson;
        }else {
            return Lesson.builder().build();
        }
    }

    public static LessonDto lessonToLessonDto(Lesson lesson){
        if (lesson != null){
            LessonDto lessonDto = LessonDto.builder()
                    .withId(lesson.getId())
                    .withDescription(lesson.getDescription())
                    .withName(lesson.getName())
                    .build();
            return lessonDto;
        }else {
            return LessonDto.builder().build();
        }
    }

}
