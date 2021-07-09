package org.foxminded.university.service;

import lombok.AllArgsConstructor;
import org.foxminded.university.dao.LessonDao;
import org.foxminded.university.domain.Page;
import org.foxminded.university.domain.Pageable;
import org.foxminded.university.entity.Lesson;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class LessonService {
    private final LessonDao lessonDao;

    public List<Lesson> findAll() {
        return lessonDao.findAll();
    }

    public Pageable<Lesson> findAll(Page page) {
        return lessonDao.findAll(page);
    }

    public Optional<Lesson> findById(Long id) {
        return lessonDao.findById(id);
    }

    public void createLesson(Lesson lesson) {
        lessonDao.create(lesson);
    }

    public void updateLesson(Lesson lesson) {
        lessonDao.update(lesson);
    }

    public void deleteLessonById(Long id) {
        lessonDao.delete(id);
    }
}
