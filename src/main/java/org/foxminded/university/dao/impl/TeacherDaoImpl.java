package org.foxminded.university.dao.impl;

import lombok.extern.slf4j.Slf4j;
import org.foxminded.university.dao.TeacherDao;
import org.foxminded.university.domain.Page;
import org.foxminded.university.domain.Pageable;
import org.foxminded.university.entity.Address;
import org.foxminded.university.entity.Teacher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@Slf4j
public class TeacherDaoImpl implements TeacherDao {

    private final JdbcTemplate jdbcTemplate;
    private static final String FIND_ALL = "SELECT * FROM teachers";
    private static final String FIND_BY_ID = "SELECT * FROM teachers where id = ?";
    private static final String INSERT_TEACHER = "INSERT INTO teachers( firstName, lastName, birthDate, phoneNumber, email, password, address_id, linkedinUrl) values(?, ?, ?, ?, ?, ?, ?, ?)";
    private static final String UPDATE_TEACHER = "UPDATE teachers set firstName = ?, lastName = ?, birthDate = ?, phoneNumber = ?, email = ?, password = ?, address_id = ?, linkedinUrl = ? WHERE id = ?";
    private static final String DELETE_TEACHER = "DELETE FROM teachers WHERE id = ?";
    private static final String FIND_ALL_QUERY_PAGEABLE = "SELECT * FROM teachers ORDER BY id OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";
    private static final String FIND_BY_EMAIL = "SELECT * FROM teachers where email = ?";

    private static final RowMapper<Teacher> teacherMapper = (resultSet, rowNum) ->
            Teacher.builder()
                    .withId(resultSet.getLong("id"))
                    .withFirstName(resultSet.getString("firstName"))
                    .withLastName(resultSet.getString("lastName"))
                    .withBirthDate(resultSet.getDate("birthDate"))
                    .withPhoneNumber(resultSet.getString("phoneNumber"))
                    .withEmail(resultSet.getString("email"))
                    .withPassword(resultSet.getString("password"))
                    .withAddress(Address.builder().withId(resultSet.getLong("address_id")).build())
                    .withLinkedinUrl(resultSet.getString("linkedinUrl"))
                    .build();

    @Autowired
    public TeacherDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void create(Teacher teacher) {
        jdbcTemplate.update(INSERT_TEACHER, teacher.getFirstName(), teacher.getLastName(), teacher.getBirthDate(),
                teacher.getPhoneNumber(), teacher.getEmail(), teacher.getPassword(),
                teacher.getAddress().getId(), teacher.getLinkedinUrl());
        log.info("created a new teacher:{}", teacher);
    }

    @Override
    public Optional<Teacher> findById(Long id) {
        return Optional.ofNullable(jdbcTemplate.queryForObject(FIND_BY_ID, teacherMapper, id));
    }

    @Override
    public void update(Teacher teacher) {
        log.info("Try to update an teacher in the db {}", teacher);
        jdbcTemplate.update(UPDATE_TEACHER, teacher.getFirstName(), teacher.getLastName(), teacher.getBirthDate(),
                teacher.getPhoneNumber(), teacher.getEmail(), teacher.getPassword(),
                teacher.getAddress().getId(), teacher.getLinkedinUrl(), teacher.getId());
    }

    @Override
    public void delete(Long id) {
        log.info("Try to delete an teacher by ID={}", id);
        jdbcTemplate.update(DELETE_TEACHER, id);
    }

    @Override
    public List<Teacher> findAll() {
        log.info("Try to find all the teachers");
        return jdbcTemplate.query(FIND_ALL, teacherMapper);
    }

    @Override
    public Pageable<Teacher> findAll(Page page) {
        log.info("Try to find all the teachers");
        final int startItems = page.getItemsPerPage() * page.getPageNumber();
        List<Teacher> teachers = jdbcTemplate.query(FIND_ALL_QUERY_PAGEABLE, teacherMapper, startItems, page.getItemsPerPage());
        return new Pageable<>(teachers, page.getPageNumber(), page.getItemsPerPage());
    }

    @Override
    public Optional<Teacher> findByEmail(String email) {
        log.info("Try to find password for the teacher by email");
        return Optional.ofNullable(jdbcTemplate.queryForObject(FIND_BY_EMAIL, teacherMapper, email));
    }
}
