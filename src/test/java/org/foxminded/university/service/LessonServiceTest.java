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

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.internal.util.JavaEightUtil.emptyOptional;

@ExtendWith(MockitoExtension.class)
class LessonServiceTest {

    @Mock
    private LessonDao dao;

    @InjectMocks
    private LessonService service;

    Lesson lesson = Lesson.builder()
            .withId(1L)
            .withName("Math")
            .withDescription("Math lesson")
            .build();

    @Test
    void findAllShouldFindAllLessons() {
        when(dao.findAll()).thenReturn(getLessons());

        assertThat(service.findAll(), hasItem(lesson));
    }

    @Test
    void findAllPageableShouldFindAllLessonsPageable() {
        Page page = new Page(0, 2);
        Pageable<Lesson> lessonPageable = new Pageable<>(getLessons(), 0, 2);
        when(dao.findAll(page)).thenReturn(lessonPageable);

        assertThat(service.findAll(page), equalTo(lessonPageable));
    }

    @Test
    void findByIdShouldFindLessonById() {
        when(dao.findById(1L)).thenReturn(Optional.of(lesson));

        assertThat(service.findById(1L), is(not(emptyOptional())));
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

    private List<Lesson> getLessons() {
        List<Lesson> lessons = new ArrayList<>();
        lessons.add(Lesson.builder()
                .withId(1L)
                .withName("Math")
                .withDescription("Math lesson")
                .build());
        lessons.add(Lesson.builder()
                .withId(2L)
                .withName("Physics")
                .withDescription("Physics lesson")
                .build());
        return lessons;
    }
}
