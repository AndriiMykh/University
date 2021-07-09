package org.foxminded.university.dao;

import org.foxminded.university.domain.Page;
import org.foxminded.university.domain.Pageable;
import org.foxminded.university.entity.Address;
import org.foxminded.university.entity.Group;
import org.foxminded.university.entity.Schedule;
import org.foxminded.university.entity.Student;
import org.foxminded.university.entity.StudiesType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.util.List;
import java.util.Optional;

@Repository
public class StudentDao extends AbstractDao<Long, Student> {

    private static final String FIND_ALL = "SELECT * FROM students";
    private static final String FIND_BY_ID = "SELECT * FROM students where id = ?";
    private static final String INSERT_STUDENT = "INSERT INTO students( firstName, lastName, birthDate, phoneNumber, email, password, address_id, group_id, studiesType) values(?, ?, ?, ?, ?, ?, ?, ?, ?)";
    private static final String UPDATE_STUDENT = "UPDATE students set firstName = ?, lastName = ?, birthDate = ?, phoneNumber = ?, email = ?, password = ?, address_id = ?, group_id = ?, studiesType = ? WHERE id = ?";
    private static final String DELETE_STUDENT = "DELETE FROM students WHERE id = ?";
    private static final String FIND_ALL_QUERY_PAGEABLE = "SELECT * FROM students ORDER BY id OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";
    private static final String FIND_ALL_SCHEDULE_FOR_STUDENT = "SELECT schedules.date, schedules.startTime, schedules.endTime FROM groups " +
            "LEFT JOIN courses_groups ON groups.id = courses_groups.groups_id "+
            "LEFT JOIN courses ON courses.id = courses_groups.courses_id "+
            "LEFT JOIN schedules on courses.schedule_id = schedules.id " +
            "LEFT JOIN schedule_dates on schedules.id = schedule_dates.schedules_id" +
            " where groups.id = ? and date is not null ";

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
    private static final RowMapper<Schedule> scheduleMapper = (resultSet, rowNum) ->
            Schedule.builder()
                    .withDate(resultSet.getDate("schedules.date").toLocalDate())
                    .withStartTime(resultSet.getTime("schedules.startTime"))
                    .withEndTime(resultSet.getTime("schedules.endTime"))
                    .build();


    @Autowired
    public StudentDao(JdbcTemplate jdbcTemplate) {
        super(jdbcTemplate);
    }

    @Override
    public void create(Student student) {
        jdbcTemplate.update(INSERT_STUDENT, student.getFirstName(), student.getLastName(), student.getBirthDate(),
                student.getPhoneNumber(), student.getEmail(), student.getPassword(),
                student.getAddress().getId(), student.getGroup().getId(),
                student.getStudiesType());
    }

    @Override
    public Optional<Student> findById(Long id) {
        return Optional.ofNullable(jdbcTemplate.queryForObject(FIND_BY_ID, studentMapper, id));
    }

    @Override
    public void update(Student student) {
        jdbcTemplate.update(UPDATE_STUDENT, student.getFirstName(), student.getLastName(), student.getBirthDate(),
                student.getPhoneNumber(), student.getEmail(), student.getPassword(),
                student.getAddress().getId(), student.getGroup().getId(),
                student.getStudiesType(), student.getId());
    }

    @Override
    public void delete(Long id) {
        jdbcTemplate.update(DELETE_STUDENT, id);
    }

    @Override
    public List<Student> findAll() {
        return jdbcTemplate.query(FIND_ALL, studentMapper);
    }

    @Override
    public Pageable<Student> findAll(Page page) {
        final int startItems = page.getItemsPerPage() * page.getPageNumber();
        List<Student> students = jdbcTemplate.query(FIND_ALL_QUERY_PAGEABLE, studentMapper, startItems, page.getItemsPerPage());
        return new Pageable<>(students, page.getPageNumber(), page.getItemsPerPage());
    }

    public List<Schedule> getScheduleForStudent(Long group_id){
        List<Schedule> dates = jdbcTemplate.query(FIND_ALL_SCHEDULE_FOR_STUDENT, scheduleMapper, group_id);
        return dates;
    }

}
