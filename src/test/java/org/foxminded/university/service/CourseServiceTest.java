package org.foxminded.university.service;

import org.foxminded.university.dao.CourseDao;
import org.foxminded.university.domain.Page;
import org.foxminded.university.domain.Pageable;
import org.foxminded.university.entity.Course;
import org.foxminded.university.entity.Lesson;
import org.foxminded.university.entity.Schedule;
import org.foxminded.university.entity.Teacher;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CourseServiceTest {

    @Mock
    private CourseDao dao;
    @InjectMocks
    private CourseService service;

    private final Course course = Course.builder()
            .withId(4L)
            .withLocation("room 35")
            .withSchedule( Schedule.builder().withId(1L).build())
            .withLesson(new Lesson(1L, "Physics", "Physic lesson"))
            .withTeacher( Teacher.builder().withId(1L).build())
            .build();

    @Test
    void findAllShouldFindAllCourses() {
        when(dao.findAll()).thenReturn(getCourses());

        assertThat(service.findAll())
                .isNotEmpty()
                .contains(course);
    }

    @Test
    void findAllShouldFindAllCoursesPageable() {
        Page page = new Page(0, 2);
        Pageable<Course> coursesPageable = new Pageable<>(getCourses(), 0,2);
        when(dao.findAll(page)).thenReturn(coursesPageable);

        assertThat(service.findAll(page))
                .isEqualTo(coursesPageable);
    }

    @Test
    void findByIdShouldFindCourseById() {
        when(dao.findById(6L)).thenReturn(Optional.of(course));

        assertThat(service.findById(6L))
                .hasValue(course);
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

    private List<Course> getCourses(){
        List<Course> courses = new ArrayList<>();
        courses.add(Course.builder()
                .withId(4L)
                .withLocation("room 35")
                .withSchedule( Schedule.builder().withId(1L).build())
                .withLesson(new Lesson(1L, "Physics", "Physic lesson"))
                .withTeacher( Teacher.builder().withId(1L).build())
                .build());
        courses.add(Course.builder()
                .withId(4L)
                .withLocation("room 35")
                .withSchedule( Schedule.builder().withId(1L).build())
                .withLesson(new Lesson(1L, "Physics", "Physic lesson"))
                .withTeacher( Teacher.builder().withId(1L).build())
                .build());
        return courses;
    }
}
