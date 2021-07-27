package org.foxminded.university.dao.impl;

import lombok.extern.slf4j.Slf4j;
import org.foxminded.university.dao.LessonDao;
import org.foxminded.university.domain.Page;
import org.foxminded.university.domain.Pageable;
import org.foxminded.university.entity.Lesson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@Slf4j
public class LessonDaoImpl implements LessonDao {

    private final JdbcTemplate jdbcTemplate;
    private static final String FIND_ALL = "SELECT * FROM lessons";
    private static final String FIND_BY_ID = "SELECT * FROM lessons where id = ?";
    private static final String INSERT_LESSON = "INSERT INTO lessons( name, description) values(?, ?)";
    private static final String UPDATE_LESSON = "UPDATE lessons set name = ?, description = ? WHERE id = ?";
    private static final String DELETE_LESSON = "DELETE FROM lessons WHERE id = ?";
    private static final String FIND_ALL_QUERY_PAGEABLE = "SELECT * FROM lessons ORDER BY id OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";
    private static final RowMapper<Lesson> lessonMapper = (resultSet, rowNum) -> Lesson.builder()
            .withId(resultSet.getLong("id"))
            .withName(resultSet.getString("name"))
            .withDescription(resultSet.getString("description"))
            .build();

    @Autowired
    public LessonDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void create(Lesson lesson) {
        jdbcTemplate.update(INSERT_LESSON, lesson.getName(), lesson.getDescription());
        log.info("created a new lesson:{}", lesson);
    }

    @Override
    public Optional<Lesson> findById(Long id) {
        try {
            log.info("Look for a lesson in the db with ID={}", id);
            return Optional.ofNullable(jdbcTemplate.queryForObject(FIND_BY_ID, lessonMapper, id));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public void update(Lesson lesson) {
        log.info("Try to update an lesson in the db {}", lesson);
        jdbcTemplate.update(UPDATE_LESSON, lesson.getName(), lesson.getDescription(), lesson.getId());
    }

    @Override
    public void delete(Long id) {
        log.info("Try to delete an lesson by ID={}", id);
        jdbcTemplate.update(DELETE_LESSON, id);
    }

    @Override
    public List<Lesson> findAll() {
        log.info("Try to find all the lessons");
        return jdbcTemplate.query(FIND_ALL, lessonMapper);
    }

    @Override
    public Pageable<Lesson> findAll(Page page) {
        log.info("Try to find all the lessons");
        final int startItems = page.getItemsPerPage() * page.getPageNumber();
        List<Lesson> lessons = jdbcTemplate.query(FIND_ALL_QUERY_PAGEABLE, lessonMapper, startItems, page.getItemsPerPage());
        return new Pageable<>(lessons, page.getPageNumber(), page.getItemsPerPage());
    }
}
