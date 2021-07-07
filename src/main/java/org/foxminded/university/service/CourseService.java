package org.foxminded.university.service;

import org.foxminded.university.dao.CourseDao;
import org.foxminded.university.domain.Page;
import org.foxminded.university.domain.Pageable;
import org.foxminded.university.entity.Course;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CourseService {
    private CourseDao courseDao;

    @Autowired
    public CourseService(CourseDao courseDao) {
        this.courseDao = courseDao;
    }

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
