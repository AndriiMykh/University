package org.foxminded.university.mapper;

import org.foxminded.university.dto.CourseDto;
import org.foxminded.university.dto.GroupDto;
import org.foxminded.university.dto.LessonDto;
import org.foxminded.university.dto.ScheduleDto;
import org.foxminded.university.dto.TeacherDto;
import org.foxminded.university.entity.Course;
import org.foxminded.university.entity.Group;
import org.foxminded.university.entity.Lesson;
import org.foxminded.university.entity.Schedule;
import org.foxminded.university.entity.Teacher;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.foxminded.university.mapper.CourseMapper.courseDtoToCourse;
import static org.foxminded.university.mapper.CourseMapper.courseToCourseDto;
import static org.junit.jupiter.api.Assertions.assertAll;

public class CourseMapperTest {

    @Test
    public void courseToCourseDtoShouldConvertCourseToCourseDto() {
        Course course = Course.builder()
                .withId(1L)
                .withLocation("Room 6")
                .withSchedule(Schedule.builder().build())
                .withLesson(Lesson.builder().build())
                .withTeacher(Teacher.builder().build())
                .withGroups(List.of(Group.builder().build()))
                .build();
        CourseDto courseDto = courseToCourseDto(course);
        assertAll(
                () -> assertThat(courseDto.getId()).isEqualTo(course.getId()),
                () -> assertThat(courseDto.getLocation()).isEqualTo(course.getLocation()),
                () -> assertThat(courseDto.getLesson()).isEqualTo(LessonDto.builder().build()),
                () -> assertThat(courseDto.getSchedule()).isEqualTo(ScheduleDto.builder().build()),
                () -> assertThat(courseDto.getTeacher()).isEqualTo(TeacherDto.builder().build()),
                () -> assertThat(courseDto.getGroups()).isEqualTo(List.of(GroupDto.builder().build()))
        );
    }

    @Test
    public void courseToCourseDtoShouldReturnEmptyDtoObject() {
        assertThat(courseToCourseDto(null)).isEqualTo(CourseDto.builder().build());
    }

    @Test
    public void courseDtoToCourseShouldConvertCourseDtoToCourse() {
        CourseDto courseDto = CourseDto.builder()
                .withId(1L)
                .withLocation("Room 6")
                .withSchedule(ScheduleDto.builder().build())
                .withLesson(LessonDto.builder().build())
                .withTeacher(TeacherDto.builder().build())
                .withGroups(List.of(GroupDto.builder().build()))
                .build();
        Course course = courseDtoToCourse(courseDto);
        assertAll(
                () -> assertThat(course.getId()).isEqualTo(courseDto.getId()),
                () -> assertThat(course.getLocation()).isEqualTo(courseDto.getLocation()),
                () -> assertThat(course.getLesson()).isEqualTo(Lesson.builder().build()),
                () -> assertThat(course.getSchedule()).isEqualTo(Schedule.builder().build()),
                () -> assertThat(course.getTeacher()).isEqualTo(Teacher.builder().build()),
                () -> assertThat(course.getGroups()).isEqualTo(List.of(Group.builder().build()))
        );
    }

    @Test
    public void courseDtoToCourseShouldReturnEmptyCourse() {
        assertThat(courseDtoToCourse(null)).isEqualTo(Course.builder().build());
    }

    @Test
    public void courseDtoToCourseShouldHaveGroupDtosNullWhenNoGroups() {
        Course course = Course.builder()
                .withGroups(null)
                .build();
        assertThat(courseToCourseDto(course).getGroups()).isNull();
    }

    @Test
    public void courseDtoToCourseShouldHaveGroupsNullWhenNoGroupDtos() {
        CourseDto courseDto = CourseDto.builder()
                .withGroups(null)
                .build();
        assertThat(courseDtoToCourse(courseDto).getGroups()).isNull();
    }
}
