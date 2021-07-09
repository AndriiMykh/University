package org.foxminded.university.dao;

import org.foxminded.university.domain.Page;
import org.foxminded.university.domain.Pageable;
import org.foxminded.university.entity.Lesson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class LessonDao extends AbstractDao<Long, Lesson> {

    private static final String FIND_ALL = "SELECT * FROM lessons";
    private static final String FIND_BY_ID = "SELECT * FROM lessons where id = ?";
    private static final String INSERT_LESSON = "INSERT INTO lessons( name, description) values(?, ?)";
    private static final String UPDATE_LESSON = "UPDATE lessons set name = ?, description = ? WHERE id = ?";
    private static final String DELETE_LESSON= "DELETE FROM lessons WHERE id = ?";
    private static final String FIND_ALL_QUERY_PAGEABLE = "SELECT * FROM lessons ORDER BY id OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";
    private static final RowMapper<Lesson> lessonMapper = (resultSet, rowNum) ->  Lesson.builder()
            .withId(resultSet.getLong("id"))
            .withName( resultSet.getString("name"))
            .withDescription(resultSet.getString("description"))
            .build();

    @Autowired
    public LessonDao(JdbcTemplate jdbcTemplate) {
        super(jdbcTemplate);
    }

    @Override
    public void create(Lesson lesson) {
        jdbcTemplate.update(INSERT_LESSON, lesson.getName(), lesson.getDescription());
    }

    @Override
    public Optional<Lesson> findById(Long id) {
        return Optional.ofNullable(jdbcTemplate.queryForObject(FIND_BY_ID, lessonMapper, id));
    }

    @Override
    public void update(Lesson lesson) {
        jdbcTemplate.update(UPDATE_LESSON, lesson.getName(), lesson.getDescription(), lesson.getId());
    }

    @Override
    public void delete(Long id) {
        jdbcTemplate.update(DELETE_LESSON, id);
    }

    @Override
    public List<Lesson> findAll() {
        return jdbcTemplate.query(FIND_ALL, lessonMapper);
    }

    @Override
    public Pageable<Lesson> findAll(Page page) {
        final int startItems = page.getItemsPerPage() * page.getPageNumber();
        List<Lesson> lessons = jdbcTemplate.query(FIND_ALL_QUERY_PAGEABLE, lessonMapper, startItems, page.getItemsPerPage());
        return new Pageable<>(lessons, page.getPageNumber(), page.getItemsPerPage());
    }
}
