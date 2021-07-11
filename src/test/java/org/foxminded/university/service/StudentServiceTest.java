package org.foxminded.university.service;

import org.assertj.core.api.AssertionsForClassTypes;
import org.foxminded.university.dao.StudentDao;
import org.foxminded.university.domain.Page;
import org.foxminded.university.domain.Pageable;
import org.foxminded.university.entity.Address;
import org.foxminded.university.entity.Group;
import org.foxminded.university.entity.Schedule;
import org.foxminded.university.entity.Student;
import org.foxminded.university.entity.StudiesType;
import org.foxminded.university.exception.ServiceException;
import org.foxminded.university.validator.PersonValidator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.internal.util.JavaEightUtil.emptyOptional;


@ExtendWith(MockitoExtension.class)
class StudentServiceTest {
    @Mock
    private StudentDao dao;

    @Mock
    private PersonValidator personValidator;

    @Mock
    private PasswordEncoder passwordEncoder;

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

        assertThat(service.findAll(), hasItem(student));
    }

    @Test
    void findAllPageableShouldReturnPageable() {
        Page page = new Page(0, 2);
        Pageable<Student> studentPageable = new Pageable<>(getStudents(), 0, 2);
        when(dao.findAll(page)).thenReturn(studentPageable);

        assertThat(service.findAll(page), equalTo(studentPageable));
    }

    @Test
    void findByIdShouldReturnAStudent() {
        when(dao.findById(1L)).thenReturn(Optional.of(student));

        assertThat(service.findById(1L), is(not(emptyOptional())));
    }

    @Test
    void createStudentShouldUseCreatMethod() {
        Student student = Student.builder()
                .withFirstName("Daniil")
                .withLastName("Danilov")
                .withBirthDate(new Date(1000))
                .withAddress(Address.builder().withId(3L).build())
                .withPhoneNumber("123132512")
                .withEmail("Mykhailo@gmail.com")
                .withPassword(null)
                .withGroup(Group.builder().withId(1L).build())
                .withStudiesType(StudiesType.FULL_TIME)
                .build();
        service.registerStudent(student);

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
    void authenticateStudentShouldReturnStudentWhenEmailAndPasswordAreCorrect() {
        when(dao.findByEmail("Mykhailo@gmail.com")).thenReturn(Optional.of(student));
        when(passwordEncoder.matches("1111", "1111")).thenReturn(true);

        assertDoesNotThrow(() -> service.authenticateStudent("Mykhailo@gmail.com", "1111"));
        AssertionsForClassTypes.assertThat(service.authenticateStudent("Mykhailo@gmail.com", "1111"))
                .isEqualTo(student);
    }

    @Test
    void authenticateStudentShouldThrowServiceExceptionWhenPasswordIsWrong() {
        when(dao.findByEmail("Mykhailo@gmail.com")).thenReturn(Optional.of(student));
        when(passwordEncoder.matches("1111", "1111")).thenReturn(false);

        assertThatThrownBy(() -> service.authenticateStudent("Mykhailo@gmail.com", "1111"))
                .isExactlyInstanceOf(ServiceException.class)
                .hasMessage("Wrong password");
    }

    @Test
    void authenticateStudentShouldThrowServiceExceptionWhenTeacherIsNotFoundByEmail() {
        when(dao.findByEmail("Mykhailo@gmail.com")).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.authenticateStudent("Mykhailo@gmail.com", "1111"))
                .isExactlyInstanceOf(ServiceException.class)
                .hasMessage("Email not found");
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

    private List<Schedule> getSchedules() {
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
