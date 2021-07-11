package org.foxminded.university.dao.impl;

import lombok.extern.slf4j.Slf4j;
import org.foxminded.university.dao.CourseDao;
import org.foxminded.university.domain.Page;
import org.foxminded.university.domain.Pageable;
import org.foxminded.university.entity.Course;
import org.foxminded.university.entity.Lesson;
import org.foxminded.university.entity.Schedule;
import org.foxminded.university.entity.Teacher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@Slf4j
public class CourseDaoImpl implements CourseDao {

    private final JdbcTemplate jdbcTemplate;
    private static final String FIND_ALL = "SELECT * FROM courses";
    private static final String FIND_BY_ID = "SELECT * FROM courses where id = ?";
    private static final String INSERT_COURSE = "INSERT INTO courses( location, schedule_id, lesson_id, teacher_id) values(?,?,?,?)";
    private static final String UPDATE_COURSE = "UPDATE courses set location = ?, schedule_id = ?, lesson_id =?, teacher_id = ? WHERE id = ?";
    private static final String DELETE_COURSE = "DELETE FROM courses WHERE id = ?";
    private static final String FIND_ALL_QUERY_PAGEABLE = "SELECT * FROM courses ORDER BY id OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";
    private static final RowMapper<Course> courseMapper = (resultSet, rowNum) -> Course.builder()
            .withId(resultSet.getLong("id"))
            .withLesson(Lesson.builder().withId(resultSet.getLong("lesson_id")).build())
            .withTeacher(Teacher.builder().withId(resultSet.getLong("lesson_id")).build())
            .withLocation(resultSet.getString("location"))
            .withSchedule(Schedule.builder().withId(resultSet.getLong("schedule_id")).build())
            .build();

    @Autowired
    public CourseDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void create(Course course) {
        jdbcTemplate.update(INSERT_COURSE, course.getLocation(), course.getSchedule().getId(), course.getLesson().getId(), course.getTeacher().getId());
        log.info("created a new course:{}", course);
    }

    @Override
    public Optional<Course> findById(Long id) {
        log.info("Look for an course in the db with ID={}", id);
        return Optional.ofNullable(jdbcTemplate.queryForObject(FIND_BY_ID, courseMapper, id));
    }

    @Override
    public void update(Course course) {
        log.info("Try to update an course in the db {}", course);
        jdbcTemplate.update(UPDATE_COURSE, course.getLocation(), course.getSchedule().getId(), course.getLesson().getId(), course.getTeacher().getId(), course.getId());
    }

    @Override
    public void delete(Long id) {
        log.info("Try to delete an course by ID={}", id);
        jdbcTemplate.update(DELETE_COURSE, id);
    }

    @Override
    public List<Course> findAll() {
        log.info("Try to find all the courses");
        return jdbcTemplate.query(FIND_ALL, courseMapper);
    }

    @Override
    public Pageable<Course> findAll(Page page) {
        log.info("Try to find all the courses");
        final int startItems = page.getItemsPerPage() * page.getPageNumber();
        List<Course> courses = jdbcTemplate.query(FIND_ALL_QUERY_PAGEABLE, courseMapper, startItems, page.getItemsPerPage());
        return new Pageable<>(courses, page.getPageNumber(), page.getItemsPerPage());
    }
}
