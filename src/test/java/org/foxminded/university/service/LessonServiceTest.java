package org.foxminded.university.service;

import org.foxminded.university.dao.LessonDao;
import org.foxminded.university.domain.Page;
import org.foxminded.university.domain.Pageable;
import org.foxminded.university.entity.Lesson;
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
class LessonServiceTest {

    @Mock
    private LessonDao dao;
    @InjectMocks
    private LessonService service;

    Lesson lesson = new Lesson(1L, "Math", "Math lesson");

    @Test
    void findAllShouldFindAllLessons() {
        when(dao.findAll()).thenReturn(getLessons());

        assertThat(service.findAll())
                .isNotEmpty()
                .contains(lesson);
    }

    @Test
    void findAllPageableShouldFindAllLessonsPageable() {
        Page page = new Page(0, 2);
        Pageable<Lesson> lessonPageable = new Pageable<>(getLessons(), 0,2);
        when(dao.findAll(page)).thenReturn(lessonPageable);

        assertThat(service.findAll(page))
                .isEqualTo(lessonPageable);
    }

    @Test
    void findByIdShouldFindLessonById() {
        when(dao.findById(1L)).thenReturn(Optional.of(lesson));

        assertThat(service.findById(1L))
                .hasValue(lesson);
    }

    @Test
    void createLessonShouldUseCreateMethod() {
        service.createLesson(lesson);

        verify(dao).create(lesson);
    }

    @Test
    void updateLessonShouldUseUpdateMethod() {
        service.updateLesson(lesson);

        verify(dao).update(lesson);
    }

    @Test
    void deleteGroupByIdShouldUseDeleteMethod() {
        Long id = 5L;
        service.deleteLessonById(id);

        verify(dao).delete(id);
    }

    private List<Lesson> getLessons(){
        List<Lesson> lessons = new ArrayList<>();
        lessons.add( new Lesson(1L, "Math", "Math lesson"));
        lessons.add( new Lesson(2L, "Physics", "Physics lesson"));
        return lessons;
    }
}
