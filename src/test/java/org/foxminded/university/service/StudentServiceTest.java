package org.foxminded.university.service;

import org.foxminded.university.dao.StudentDao;
import org.foxminded.university.domain.Page;
import org.foxminded.university.domain.Pageable;
import org.foxminded.university.entity.Address;
import org.foxminded.university.entity.Group;
import org.foxminded.university.entity.Schedule;
import org.foxminded.university.entity.Student;
import org.foxminded.university.entity.StudiesType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class StudentServiceTest {
    @Mock
    private StudentDao dao;
    @InjectMocks
    private StudentService service;

    private final Student student = Student.builder()
            .withId(1L)
            .withFirstName("Daniil")
            .withLastName("Danilov")
            .withBirthDate(new Date(1000))
            .withAddress(Address.builder().withId(3L).build())
            .withPhoneNumber("123132512")
            .withEmail("Mykhailo@gmail.com")
            .withPassword("1111")
            .withGroup(Group.builder().withId(1L).build())
            .withStudiesType(StudiesType.FULL_TIME)
            .build();

    @Test
    void findAllShouldReturnStudentList() {
        when(dao.findAll()).thenReturn(getStudents());

        assertThat(service.findAll())
                .isNotEmpty()
                .contains(student);
    }

    @Test
    void findAllPageableShouldReturnPageable() {
        Page page = new Page(0, 2);
        Pageable<Student> studentPageable = new Pageable<>(getStudents(), 0,2);
        when(dao.findAll(page)).thenReturn(studentPageable);

        assertThat(service.findAll(page))
                .isEqualTo(studentPageable);
    }

    @Test
    void findByIdShouldReturnAStudent() {
        when(dao.findById(1L)).thenReturn(Optional.of(student));

        assertThat(service.findById(1L))
                .hasValue(student);
    }

    @Test
    void createStudentShouldUseCreatMethod() {
        service.createStudent(student);

        verify(dao).create(student);
    }

    @Test
    void updateStudentShouldUseUpdateMethod() {
        service.updateStudent(student);

        verify(dao).update(student);
    }

    @Test
    void deleteByIdShouldUseDeleteMethod() {
        Long id = 5L;
        service.deleteStudentById(id);

        verify(dao).delete(id);
    }

    @Test
    void getScheduleForTodayShouldReturnScheduleForDay(){
        Long id = 5L;
        when(dao.getScheduleForStudent(id)).thenReturn(getSchedules());
        assertThat(service.getScheduleForToday(id))
                .hasSize(2);
    }

    @Test
    void getScheduleForTodayShouldReturnScheduleForMonth(){
        Long id = 5L;
        when(dao.getScheduleForStudent(id)).thenReturn(getSchedules());
        assertThat(service.getScheduleForMonth(id))
                .hasSize(3);
    }

    private List<Student> getStudents() {
        List<Student> students = new ArrayList<>();
        students.add(Student.builder()
                .withId(1L)
                .withFirstName("Daniil")
                .withLastName("Danilov")
                .withBirthDate(new Date(1000))
                .withAddress(Address.builder().withId(3L).build())
                .withPhoneNumber("123132512")
                .withEmail("Mykhailo@gmail.com")
                .withPassword("1111")
                .withGroup(Group.builder().withId(1L).build())
                .withStudiesType(StudiesType.FULL_TIME)
                .build());
        students.add(Student.builder()
                .withId(2L)
                .withFirstName("Kyrylo")
                .withLastName("Dymin")
                .withBirthDate(new Date(1000))
                .withAddress(Address.builder().withId(3L).build())
                .withPhoneNumber("123132512")
                .withEmail("Kyrylo@gmail.com")
                .withPassword("1111")
                .withGroup(Group.builder().withId(1L).build())
                .withStudiesType(StudiesType.FULL_TIME)
                .build());
        return students;
    }

    private List<Schedule> getSchedules(){
        List<Schedule> schedules = new ArrayList<>();
        schedules.add(Schedule.builder()
                .withDate(LocalDate.now())
                .withStartTime(new Time(1000))
                .withEndTime(new Time(2000))
                .build());
        schedules.add(Schedule.builder()
                .withDate(LocalDate.now())
                .withStartTime(new Time(2500))
                .withEndTime(new Time(3500))
                .build());
        schedules.add(Schedule.builder()
                .withDate(LocalDate.now().plusWeeks(1))
                .withStartTime(new Time(2500))
                .withEndTime(new Time(3500))
                .build());
        return schedules;
    }
}
