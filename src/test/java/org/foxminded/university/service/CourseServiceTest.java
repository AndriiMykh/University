package org.foxminded.university.service;

import org.foxminded.university.dao.CourseDao;
import org.foxminded.university.domain.Page;
import org.foxminded.university.domain.Pageable;
import org.foxminded.university.entity.Course;
import org.foxminded.university.entity.Lesson;
import org.foxminded.university.entity.Schedule;
import org.foxminded.university.entity.Teacher;
import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.internal.util.JavaEightUtil.emptyOptional;

@ExtendWith(MockitoExtension.class)
class CourseServiceTest {

    @Mock
    private CourseDao dao;

    @InjectMocks
    private CourseService service;

    private final Course course = Course.builder()
            .withId(4L)
            .withLocation("room 35")
            .withSchedule(Schedule.builder().withId(1L).build())
            .withLesson(Lesson.builder()
                    .withId(1L)
                    .withName("Physics")
                    .withDescription("Physic lesson")
                    .build())
            .withTeacher(Teacher.builder().withId(1L).build())
            .build();

    @Test
    void findAllShouldFindAllCourses() {
        when(dao.findAll()).thenReturn(getCourses());

        assertThat(service.findAll(), hasItem(course));
    }

    @Test
    void findAllShouldFindAllCoursesPageable() {
        Page page = new Page(0, 2);
        Pageable<Course> coursesPageable = new Pageable<>(getCourses(), 0, 2);
        when(dao.findAll(page)).thenReturn(coursesPageable);

        assertThat(service.findAll(page), equalTo(coursesPageable));
    }

    @Test
    void findByIdShouldFindCourseById() {
        when(dao.findById(6L)).thenReturn(Optional.of(course));

        assertThat(service.findById(6L), is(not(emptyOptional())));
    }

    @Test
    void createCourse() {
        service.createCourse(course);

        verify(dao).create(course);
    }

    @Test
    void updateCourse() {
        service.updateCourse(course);

        verify(dao).update(course);
    }

    @Test
    void deleteCourseById() {
        Long id = 5L;
        service.deleteCourseById(id);

        verify(dao).delete(id);
    }

    private List<Course> getCourses() {
        List<Course> courses = new ArrayList<>();
        courses.add(Course.builder()
                .withId(4L)
                .withLocation("room 35")
                .withSchedule(Schedule.builder().withId(1L).build())
                .withLesson(Lesson.builder()
                        .withId(1L)
                        .withName("Physics")
                        .withDescription("Physic lesson")
                        .build())
                .withTeacher(Teacher.builder().withId(1L).build())
                .build());
        courses.add(Course.builder()
                .withId(4L)
                .withLocation("room 35")
                .withSchedule(Schedule.builder().withId(1L).build())
                .withLesson(Lesson.builder()
                        .withId(1L)
                        .withName("Physics")
                        .withDescription("Physic lesson")
                        .build())
                .withTeacher(Teacher.builder().withId(1L).build())
                .build());
        return courses;
    }
}
