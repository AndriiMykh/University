package org.foxminded.university.dao;

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
public class GroupDao extends AbstractDao<Long, Group>{

    private static final String FIND_ALL = "SELECT * FROM groups";
    private static final String FIND_BY_ID = "SELECT * FROM groups where id = ?";
    private static final String INSERT_COURSE = "INSERT INTO groups( name) values(?)";
    private static final String UPDATE_COURSE = "UPDATE groups set name = ? WHERE id = ?";
    private static final String DELETE_COURSE = "DELETE FROM groups WHERE id = ?";
    private static final String FIND_ALL_QUERY_PAGEABLE = "SELECT * FROM groups ORDER BY id OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";
    private static final RowMapper<Group> groupMapper = (resultSet, rowNum) -> new Group(resultSet.getLong("id"), resultSet.getString("name"));

    @Autowired
    public GroupDao(JdbcTemplate jdbcTemplate) {
        super(jdbcTemplate);
    }

    @Override
    public void create(Group group) {
        jdbcTemplate.update(INSERT_COURSE, group.getName());
    }

    @Override
    public Optional<Group> findById(Long id) {
        return Optional.ofNullable(jdbcTemplate.queryForObject(FIND_BY_ID, groupMapper, id));
    }

    @Override
    public void update(Group group) {
        jdbcTemplate.update(UPDATE_COURSE, group.getName(), group.getId());
    }

    @Override
    public void delete(Long id) {
        jdbcTemplate.update(DELETE_COURSE, id);
    }

    @Override
    public List<Group> findAll() {
        return jdbcTemplate.query(FIND_ALL, groupMapper);
    }

    @Override
    public Pageable<Group> findAll(Page page) {
        final int startItems = page.getItemsPerPage() * page.getPageNumber();
        List<Group> groups = jdbcTemplate.query(FIND_ALL_QUERY_PAGEABLE, groupMapper, startItems, page.getItemsPerPage());
        return new Pageable<>(groups, page.getPageNumber(), page.getItemsPerPage());
    }
}
