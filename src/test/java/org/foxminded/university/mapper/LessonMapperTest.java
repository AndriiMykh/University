package org.foxminded.university.mapper;

import org.foxminded.university.dto.LessonDto;
import org.foxminded.university.entity.Lesson;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.foxminded.university.mapper.LessonMapper.lessonDtoToLesson;
import static org.foxminded.university.mapper.LessonMapper.lessonToLessonDto;
import static org.junit.jupiter.api.Assertions.assertAll;

public class LessonMapperTest {

    @Test
    public void lessonToLessonDtoShouldConvertLessonToLessonDto() {
        Lesson lesson = Lesson.builder()
                .withId(1L)
                .withName("ds-41")
                .withDescription("Some description")
                .build();
        LessonDto lessonDto = lessonToLessonDto(lesson);
        assertAll(
                () -> assertThat(lessonDto.getId()).isEqualTo(lesson.getId()),
                () -> assertThat(lessonDto.getName()).isEqualTo(lesson.getName()),
                () -> assertThat(lessonDto.getDescription()).isEqualTo(lesson.getDescription())
        );
    }


    @Test
    public void lessonToLessonDtoShouldReturnEmptyDtoGroup() {
        assertThat(lessonToLessonDto(null)).isEqualTo(LessonDto.builder().build());
    }

    @Test
    public void lessonDtoToLessonShouldConvertLessonDtoToLesson() {
        LessonDto lessonDto = LessonDto.builder()
                .withId(1L)
                .withName("ds-41")
                .withDescription("Some description")
                .build();
        Lesson lesson = lessonDtoToLesson(lessonDto);
        assertAll(
                () -> assertThat(lesson.getId()).isEqualTo(lessonDto.getId()),
                () -> assertThat(lesson.getName()).isEqualTo(lessonDto.getName()),
                () -> assertThat(lesson.getDescription()).isEqualTo(lessonDto.getDescription())
        );
    }

    @Test
    public void lessonDtoToLessonShouldReturnEmptyDtoGroup() {
        assertThat(lessonDtoToLesson(null)).isEqualTo(Lesson.builder().build());
    }
}
