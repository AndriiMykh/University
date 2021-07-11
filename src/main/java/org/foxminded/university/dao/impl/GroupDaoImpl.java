package org.foxminded.university.dao.impl;

import lombok.extern.slf4j.Slf4j;
import org.foxminded.university.dao.GroupDao;
import org.foxminded.university.domain.Page;
import org.foxminded.university.domain.Pageable;
import org.foxminded.university.entity.Group;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@Slf4j
public class GroupDaoImpl implements GroupDao {

    private final JdbcTemplate jdbcTemplate;
    private static final String FIND_ALL = "SELECT * FROM groups";
    private static final String FIND_BY_ID = "SELECT * FROM groups where id = ?";
    private static final String INSERT_GROUP = "INSERT INTO groups( name) values(?)";
    private static final String UPDATE_GROUP = "UPDATE groups set name = ?, availablePlaces=? WHERE id = ?";
    private static final String DELETE_GROUP = "DELETE FROM groups WHERE id = ?";
    private static final String FIND_ALL_QUERY_PAGEABLE = "SELECT * FROM groups ORDER BY id OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";
    private static final String ASSIGN_STUDENT_TO_GROUP = "UPDATE students set group_id = ? where id = ?";
    private static final RowMapper<Group> groupMapper = (resultSet, rowNum) ->
            Group.builder()
                    .withId(resultSet.getLong("id"))
                    .withName(resultSet.getString("name"))
                    .withAvailablePlaces(resultSet.getInt("availablePlaces"))
                    .build();

    @Autowired
    public GroupDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void create(Group group) {
        jdbcTemplate.update(INSERT_GROUP, group.getName());
        log.info("created a new group:{}", group);
    }

    @Override
    public Optional<Group> findById(Long id) {
        log.info("Look for an group in the db with ID={}", id);
        return Optional.ofNullable(jdbcTemplate.queryForObject(FIND_BY_ID, groupMapper, id));
    }

    @Override
    public void update(Group group) {
        log.info("Try to update an group in the db {}", group);
        jdbcTemplate.update(UPDATE_GROUP, group.getName(), group.getAvailablePlaces(), group.getId());
    }

    @Override
    public void delete(Long id) {
        log.info("Try to delete an group by ID={}", id);
        jdbcTemplate.update(DELETE_GROUP, id);
    }

    @Override
    public List<Group> findAll() {
        log.info("Try to find all the groups");
        return jdbcTemplate.query(FIND_ALL, groupMapper);
    }

    @Override
    public Pageable<Group> findAll(Page page) {
        log.info("Try to find all the groups");
        final int startItems = page.getItemsPerPage() * page.getPageNumber();
        List<Group> groups = jdbcTemplate.query(FIND_ALL_QUERY_PAGEABLE, groupMapper, startItems, page.getItemsPerPage());
        return new Pageable<>(groups, page.getPageNumber(), page.getItemsPerPage());
    }

    @Override
    public void assignStudentToGroup(Long studentId, Group group) {
        jdbcTemplate.update(ASSIGN_STUDENT_TO_GROUP, group.getId(), studentId);
        update(group);
    }
}
