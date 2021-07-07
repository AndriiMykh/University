package org.foxminded.university.service;

import org.foxminded.university.dao.LessonDao;
import org.foxminded.university.domain.Page;
import org.foxminded.university.domain.Pageable;
import org.foxminded.university.entity.Lesson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LessonService {
    private LessonDao lessonDao;

    @Autowired
    public LessonService(LessonDao lessonDao) {
        this.lessonDao = lessonDao;
    }

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
