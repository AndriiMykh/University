package org.foxminded.university.dao;

import org.foxminded.university.domain.Page;
import org.foxminded.university.entity.Lesson;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

@SpringJUnitConfig(SpringTestConfig.class)
@Sql(scripts = {"classpath:schemaTest.sql", "classpath:data.sql"})
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class LessonsDaoTest {

    @Autowired
    private LessonDao lessonDao;

    @Test
    void createShouldCreateALesson() {
        int sizeBefore = lessonDao.findAll().size();
        Lesson lesson = Lesson.builder()
                .withId(5L)
                .withName("English")
                .withDescription("English lesson")
                .build();
        lessonDao.create(lesson);
        assertThat(lessonDao.findAll())
                .hasSize(sizeBefore + 1)
                .contains(lesson);
    }

    @Test
    void findByIdShouldFindLesson() {
        Lesson lesson = Lesson.builder()
                .withId(1L)
                .withName("Physics")
                .withDescription("Physic lesson")
                .build();
        assertThat(lessonDao.findById(1L)).hasValue(lesson);
    }

    @Test
    void updateShouldUpdateLesson() {
        Lesson lesson = Lesson.builder()
                .withId(1L)
                .withName("English")
                .withDescription("English lesson")
                .build();
        lessonDao.update(lesson);
        assertThat(lessonDao.findById(1L)).hasValue(lesson);
    }

    @Test
    void deleteShouldDeleteLesson() {
        int sizeBefore = lessonDao.findAll().size();
        lessonDao.delete(1L);
        int sizeAfter = lessonDao.findAll().size();
        assertThat(sizeAfter)
                .isEqualTo(sizeBefore - 1);
    }

    @Test
    void findAllShouldFindAllLessons() {
        Lesson lesson = Lesson.builder()
                .withId(1L)
                .withName("Physics")
                .withDescription("Physic lesson")
                .build();
        assertThat(lessonDao.findAll())
                .isNotEmpty()
                .contains(lesson);
    }

    @Test
    void findAllShouldFindAllLessonsPageable() {
        Page page = new Page(0, 3);
        assertThat(lessonDao.findAll(page).getItems())
                .hasSize(3);
    }
}
