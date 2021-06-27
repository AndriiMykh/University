package org.foxminded.university.dao;

import org.foxminded.university.domain.Page;
import org.foxminded.university.domain.Pageable;
import org.foxminded.university.entity.Schedule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class ScheduleDao extends AbstractDao<Long, Schedule> {

    private static final String FIND_ALL = "SELECT * FROM schedules";
    private static final String FIND_BY_ID = "SELECT * FROM schedules where id = ?";
    private static final String INSERT_SCHEDULE = "INSERT INTO schedules(  date, startTime, endTime) values(?, ?, ?)";
    private static final String UPDATE_SCHEDULE = "UPDATE schedules set date = ?, startTime = ?, endTime = ? WHERE id = ?";
    private static final String DELETE_SCHEDULE = "DELETE FROM schedules WHERE id = ?";
    private static final String FIND_ALL_QUERY_PAGEABLE = "SELECT * FROM schedules ORDER BY id OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";
    private static final RowMapper<Schedule> scheduleMapper = (resultSet, rowNum) ->
             Schedule.builder().
                    withId(resultSet.getLong("id"))
                    .withDate(resultSet.getDate("date"))
                    .withStartTime(resultSet.getTime("startTime"))
                    .withEndTime(resultSet.getTime("endTime"))
                    .build();

    @Autowired
    public ScheduleDao(JdbcTemplate jdbcTemplate) {
        super(jdbcTemplate);
    }

    @Override
    public void create(Schedule schedule) {
        jdbcTemplate.update(INSERT_SCHEDULE,  schedule.getDate(), schedule.getStartTime(), schedule.getEndTime());
    }

    @Override
    public Optional<Schedule> findById(Long id) {
        return Optional.ofNullable(jdbcTemplate.queryForObject(FIND_BY_ID, scheduleMapper, id));
    }

    @Override
    public void update(Schedule schedule) {
        jdbcTemplate.update(UPDATE_SCHEDULE,  schedule.getDate(), schedule.getStartTime(), schedule.getEndTime(), schedule.getId());
    }

    @Override
    public void delete(Long id) {
        jdbcTemplate.update(DELETE_SCHEDULE, id);
    }

    @Override
    public List<Schedule> findAll() {
        return jdbcTemplate.query(FIND_ALL, scheduleMapper);
    }

    @Override
    Pageable<Schedule> findAll(Page page) {
        final int startItems = page.getItemsPerPage() * page.getPageNumber();
        List<Schedule> schedules = jdbcTemplate.query(FIND_ALL_QUERY_PAGEABLE, scheduleMapper, startItems, page.getItemsPerPage());
        return new Pageable<>(schedules, page.getPageNumber(), page.getItemsPerPage());
    }
}