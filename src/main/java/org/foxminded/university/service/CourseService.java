package org.foxminded.university.service;

import lombok.AllArgsConstructor;
import org.foxminded.university.dao.CourseDao;
import org.foxminded.university.domain.Page;
import org.foxminded.university.domain.Pageable;
import org.foxminded.university.entity.Course;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class CourseService {
    private final CourseDao courseDao;

    public List<Course> findAll() {
        return courseDao.findAll();
    }

    public Pageable<Course> findAll(Page page) {
        return courseDao.findAll(page);
    }

    public Optional<Course> findById(Long id) {
        return courseDao.findById(id);
    }

    public void createCourse(Course course) {
        courseDao.create(course);
    }

    public void updateCourse(Course course) {
        courseDao.update(course);
    }

    public void deleteCourseById(Long id) {
        courseDao.delete(id);
    }
}
