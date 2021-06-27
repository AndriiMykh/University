package org.foxminded.university.dao;

import org.foxminded.university.domain.Page;
import org.foxminded.university.entity.Course;
import org.foxminded.university.entity.Lesson;
import org.foxminded.university.entity.Schedule;
import org.foxminded.university.entity.Teacher;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

@SpringJUnitConfig(SpringTestConfig.class)
@Sql(scripts = { "classpath:schemaTest.sql", "classpath:data.sql"})
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class CourseDaoTest {

    @Autowired
    private CourseDao courseDao;

    @Test
    void createShouldCreateACourse() {
        int sizeBefore = courseDao.findAll().size();
        Course course = Course.builder()
                .withId(4L)
                .withLocation("room 35")
                .withSchedule( Schedule.builder().withId(1L).build())
                .withLesson(new Lesson(1L, "Physics", "Physic lesson"))
                .withTeacher( Teacher.builder().withId(1L).build())
                .build();
        courseDao.create(course);
        assertThat(courseDao.findAll())
                .hasSize(sizeBefore+1)
                .contains(course);
    }

    @Test
    void findByIdShouldFindCourse() {
        Course course = Course.builder()
                .withId(1L)
                .withLocation("room 5")
                .build();
        assertThat(courseDao.findById(1L)).hasValue(course);
    }

    @Test
    void updateShouldUpdateCourse() {
        Course course =  Course.builder()
                .withId(1L)
                .withLocation("room 35")
                .withSchedule( Schedule.builder().withId(1L).build())
                .withLesson(new Lesson(1L, "Physics", "Physic lesson"))
                .withTeacher( Teacher.builder().withId(1L).build())
                .build();
        courseDao.update(course);
        assertThat(courseDao.findById(1L)).hasValue(course);
    }

    @Test
    void deleteShouldDeleteCourse() {
        int sizeBefore = courseDao.findAll().size();
        courseDao.delete(1L);
        int sizeAfter = courseDao.findAll().size();
        assertThat(sizeAfter)
                .isEqualTo(sizeBefore-1);
    }

    @Test
    void findAllShouldFindAllCourses() {
        Course course =  Course.builder()
                .withId(1L)
                .withLocation("room 5")
                .build();
        assertThat(courseDao.findAll())
                .isNotEmpty()
                .contains(course);
    }

    @Test
    void findAllShouldFindAllCoursesPageable() {
        Page page = new Page(0,3);
        assertThat(courseDao.findAll(page).getItems())
                .hasSize(3);
    }
}
