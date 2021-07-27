package org.foxminded.university.dao.impl;

import lombok.extern.slf4j.Slf4j;
import org.foxminded.university.dao.ScheduleDao;
import org.foxminded.university.domain.Page;
import org.foxminded.university.domain.Pageable;
import org.foxminded.university.entity.Schedule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
@Slf4j
public class ScheduleDaoImpl implements ScheduleDao {

    private final JdbcTemplate jdbcTemplate;
    private static final String FIND_ALL = "SELECT * FROM schedules";
    private static final String FIND_BY_ID = "SELECT * FROM schedules where id = ?";
    private static final String INSERT_SCHEDULE = "INSERT INTO schedules(  date, startTime, endTime) values(?, ?, ?)";
    private static final String UPDATE_SCHEDULE = "UPDATE schedules set date = ?, startTime = ?, endTime = ? WHERE id = ?";
    private static final String DELETE_SCHEDULE = "DELETE FROM schedules WHERE id = ?";
    private static final String FIND_ALL_QUERY_PAGEABLE = "SELECT * FROM schedules ORDER BY id OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";
    private static final String INSERT_DATES = "INSERT INTO schedule_dates(schedules_id, dates) values (?,?)";
    private static final String FIND_DATES = "SELECT * FROM schedule_dates where schedules_id = ?";
    private static final String FIND_ALL_SCHEDULE_FOR_STUDENT = "SELECT schedules.date date, schedules.startTime startTime, schedules.endTime endTime FROM groups " +
            "LEFT JOIN courses_groups ON groups.id = courses_groups.groups_id " +
            "LEFT JOIN courses ON courses.id = courses_groups.courses_id " +
            "LEFT JOIN schedules on courses.schedule_id = schedules.id " +
            "LEFT JOIN schedule_dates on schedules.id = schedule_dates.schedules_id" +
            " where groups.id = ? and date is not null ";
    private static final String FIND_ALL_SCHEDULE_FOR_TEACHER = "SELECT schedule_dates.dates date, schedules.startTime startTime, schedules.endTime endTime" +
            " FROM courses LEFT JOIN schedules on courses.schedule_id = schedules.id " +
            "LEFT JOIN schedule_dates on schedules.id = schedule_dates.schedules_id " +
            "WHERE courses.teacher_id = ? and schedule_dates.dates is not null";
    private static final RowMapper<Schedule> scheduleMapper = (resultSet, rowNum) ->
            Schedule.builder()
                    .withId(resultSet.getLong("id"))
                    .withDate(resultSet.getDate("date").toLocalDate())
                    .withStartTime(resultSet.getTime("startTime"))
                    .withEndTime(resultSet.getTime("endTime"))
                    .build();


    private static final RowMapper<Schedule> scheduleMapperWithoutId = (resultSet, rowNum) ->
            Schedule.builder()
                    .withDate(resultSet.getDate("date").toLocalDate())
                    .withStartTime(resultSet.getTime("startTime"))
                    .withEndTime(resultSet.getTime("endTime"))
                    .build();

    @Autowired
    public ScheduleDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void create(Schedule schedule) {
        jdbcTemplate.update(INSERT_SCHEDULE, schedule.getDate(), schedule.getStartTime(), schedule.getEndTime());
        log.info("created a new schedule:{}", schedule);
    }

    @Override
    public Optional<Schedule> findById(Long id) {
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(FIND_BY_ID, scheduleMapper, id));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public void update(Schedule schedule) {
        log.info("Try to update an schedule in the db {}", schedule);
        jdbcTemplate.update(UPDATE_SCHEDULE, schedule.getDate(), schedule.getStartTime(), schedule.getEndTime(), schedule.getId());
    }

    @Override
    public void delete(Long id) {
        log.info("Try to delete an schedule by ID={}", id);
        jdbcTemplate.update(DELETE_SCHEDULE, id);
    }

    @Override
    public List<Schedule> findAll() {
        log.info("Try to find all the schedules");
        return jdbcTemplate.query(FIND_ALL, scheduleMapper);
    }

    @Override
    public Pageable<Schedule> findAll(Page page) {
        log.info("Try to find all the schedules");
        final int startItems = page.getItemsPerPage() * page.getPageNumber();
        List<Schedule> schedules = jdbcTemplate.query(FIND_ALL_QUERY_PAGEABLE, scheduleMapper, startItems, page.getItemsPerPage());
        return new Pageable<>(schedules, page.getPageNumber(), page.getItemsPerPage());
    }

    @Override
    public void insertDates(Long id, LocalDate localDate) {
        log.info("Try to insert date the schedules");
        jdbcTemplate.update(INSERT_DATES, id, localDate);
    }

    @Override
    public List<LocalDate> getDates(Long id) {
        List<Date> dates = jdbcTemplate.query(FIND_DATES, (resultSet, i) -> resultSet.getDate("dates"), id);
        return dates.stream()
                .map(Date::toLocalDate)
                .collect(Collectors.toList());
    }

    @Override
    public List<Schedule> getScheduleForStudent(Long id) {
        log.info("Try to find schedule for the students");
        return jdbcTemplate.query(FIND_ALL_SCHEDULE_FOR_STUDENT, scheduleMapperWithoutId, id);
    }

    @Override
    public List<Schedule> getScheduleForTeacher(Long id) {
        log.info("Try to find schedule for the teachers");
        return jdbcTemplate.query(FIND_ALL_SCHEDULE_FOR_TEACHER, scheduleMapperWithoutId, id);
    }
}
