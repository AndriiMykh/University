package org.foxminded.university.dao;

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
public class CourseDao extends AbstractDao<Long, Course> {

    private static final String FIND_ALL = "SELECT * FROM courses";
    private static final String FIND_BY_ID = "SELECT * FROM courses where id = ?";
    private static final String INSERT_COURSE = "INSERT INTO courses( location, schedule_id, lesson_id, teacher_id) values(?,?,?,?)";
    private static final String UPDATE_COURSE = "UPDATE courses set location = ?, schedule_id = ?, lesson_id =?, teacher_id = ? WHERE id = ?";
    private static final String DELETE_COURSE = "DELETE FROM courses WHERE id = ?";
    private static final String FIND_ALL_QUERY_PAGEABLE = "SELECT * FROM courses ORDER BY id OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";
    private static final RowMapper<Course> courseMapper = (resultSet, rowNum) -> Course.builder()
            .withId(resultSet.getLong("id"))
            .withLesson(new Lesson(resultSet.getLong("lesson_id")))
            .withTeacher( Teacher.builder().withId(resultSet.getLong("lesson_id")).build())
            .withLocation(resultSet.getString("location"))
            .withSchedule( Schedule.builder().withId(resultSet.getLong("schedule_id")).build())
            .build();

    @Autowired
    public CourseDao(JdbcTemplate jdbcTemplate) {
        super(jdbcTemplate);
    }

    @Override
    public void create(Course course) {
        jdbcTemplate.update(INSERT_COURSE, course.getLocation(), course.getSchedule().getId(), course.getLesson().getId(), course.getTeacher().getId());
    }

    @Override
    public Optional<Course> findById(Long id) {
        return Optional.ofNullable(jdbcTemplate.queryForObject(FIND_BY_ID, courseMapper, id));
    }

    @Override
    public void update(Course course) {
        jdbcTemplate.update(UPDATE_COURSE, course.getLocation(), course.getSchedule().getId(), course.getLesson().getId(), course.getTeacher().getId(), course.getId());
    }

    @Override
    public void delete(Long id) {
        jdbcTemplate.update(DELETE_COURSE, id);
    }

    @Override
    public List<Course> findAll() {
        return jdbcTemplate.query(FIND_ALL, courseMapper);
    }

    @Override
    public Pageable<Course> findAll(Page page) {
        final int startItems = page.getItemsPerPage() * page.getPageNumber();
        List<Course> courses = jdbcTemplate.query(FIND_ALL_QUERY_PAGEABLE, courseMapper, startItems, page.getItemsPerPage());
        return new Pageable<>(courses, page.getPageNumber(), page.getItemsPerPage());
    }

}
