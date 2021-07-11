package org.foxminded.university.dao.impl;

import lombok.extern.slf4j.Slf4j;
import org.foxminded.university.dao.StudentDao;
import org.foxminded.university.domain.Page;
import org.foxminded.university.domain.Pageable;
import org.foxminded.university.entity.Address;
import org.foxminded.university.entity.Group;
import org.foxminded.university.entity.Student;
import org.foxminded.university.entity.StudiesType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@Slf4j
public class StudentDaoImpl implements StudentDao {

    private final JdbcTemplate jdbcTemplate;
    private static final String FIND_ALL = "SELECT * FROM students";
    private static final String FIND_BY_ID = "SELECT * FROM students where id = ?";
    private static final String INSERT_STUDENT = "INSERT INTO students( firstName, lastName, birthDate, phoneNumber, email, password, address_id, group_id, studiesType) values(?, ?, ?, ?, ?, ?, ?, ?, ?)";
    private static final String UPDATE_STUDENT = "UPDATE students set firstName = ?, lastName = ?, birthDate = ?, phoneNumber = ?, email = ?, password = ?, address_id = ?, group_id = ?, studiesType = ? WHERE id = ?";
    private static final String DELETE_STUDENT = "DELETE FROM students WHERE id = ?";
    private static final String FIND_ALL_QUERY_PAGEABLE = "SELECT * FROM students ORDER BY id OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";
    private static final String FIND_BY_EMAIL = "SELECT * FROM students where email = ?";

    private static final RowMapper<Student> studentMapper = (resultSet, rowNum) ->
            Student.builder()
                    .withId(resultSet.getLong("id"))
                    .withFirstName(resultSet.getString("firstName"))
                    .withLastName(resultSet.getString("lastName"))
                    .withBirthDate(resultSet.getDate("birthDate"))
                    .withPhoneNumber(resultSet.getString("phoneNumber"))
                    .withEmail(resultSet.getString("email"))
                    .withPassword(resultSet.getString("password"))
                    .withAddress(Address.builder().withId(resultSet.getLong("address_id")).build())
                    .withGroup(Group.builder().withId(resultSet.getLong("group_id")).build())
                    .withStudiesType(StudiesType.getType(resultSet.getString("studiesType")))
                    .build();

    @Autowired
    public StudentDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void create(Student student) {
        jdbcTemplate.update(INSERT_STUDENT, student.getFirstName(), student.getLastName(), student.getBirthDate(),
                student.getPhoneNumber(), student.getEmail(), student.getPassword(),
                student.getAddress().getId(), student.getGroup().getId(),
                student.getStudiesType());
        log.info("created a new student:{}", student);
    }

    @Override
    public Optional<Student> findById(Long id) {
        return Optional.ofNullable(jdbcTemplate.queryForObject(FIND_BY_ID, studentMapper, id));
    }

    @Override
    public void update(Student student) {
        log.info("Try to update an student in the db {}", student);
        jdbcTemplate.update(UPDATE_STUDENT, student.getFirstName(), student.getLastName(), student.getBirthDate(),
                student.getPhoneNumber(), student.getEmail(), student.getPassword(),
                student.getAddress().getId(), student.getGroup().getId(),
                student.getStudiesType(), student.getId());
    }

    @Override
    public void delete(Long id) {
        log.info("Try to delete an student by ID={}", id);
        jdbcTemplate.update(DELETE_STUDENT, id);
    }

    @Override
    public List<Student> findAll() {
        log.info("Try to find all the students");
        return jdbcTemplate.query(FIND_ALL, studentMapper);
    }

    @Override
    public Pageable<Student> findAll(Page page) {
        log.info("Try to find all the students");
        final int startItems = page.getItemsPerPage() * page.getPageNumber();
        List<Student> students = jdbcTemplate.query(FIND_ALL_QUERY_PAGEABLE, studentMapper, startItems, page.getItemsPerPage());
        return new Pageable<>(students, page.getPageNumber(), page.getItemsPerPage());
    }

    @Override
    public Optional<Student> findByEmail(String email) {
        log.info("Try to find password for the student by email");
        return Optional.ofNullable(jdbcTemplate.queryForObject(FIND_BY_EMAIL, studentMapper, email));
    }
}
