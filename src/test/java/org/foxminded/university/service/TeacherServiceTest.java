package org.foxminded.university.service;

import org.foxminded.university.dao.AddressDao;
import org.foxminded.university.dao.TeacherDao;
import org.foxminded.university.domain.Page;
import org.foxminded.university.domain.Pageable;
import org.foxminded.university.dto.TeacherDto;
import org.foxminded.university.entity.Address;
import org.foxminded.university.entity.Schedule;
import org.foxminded.university.entity.Teacher;
import org.foxminded.university.exception.ServiceException;
import org.foxminded.university.mapper.TeacherMapper;
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
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.foxminded.university.mapper.TeacherMapper.teacherToTeacherDto;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.internal.util.JavaEightUtil.emptyOptional;

@ExtendWith(MockitoExtension.class)
class TeacherServiceTest {
    @Mock
    private TeacherDao dao;

    @Mock
    private PersonValidator personValidator;

    @Mock
    private AddressDao addressDao;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private TeacherService service;

    private final Teacher teacher = Teacher.builder()
            .withId(1L)
            .withFirstName("Mykhailo")
            .withLastName("Dymin")
            .withBirthDate(new Date(1000))
            .withAddress(Address.builder().withId(3L).build())
            .withPhoneNumber("123132512")
            .withEmail("Mykhailo@gmail.com")
            .withPassword("1111")
            .withLinkedinUrl("linkedin.com/in/Mykhailo/")
            .build();

    @Test
    void findAllShouldFindAllTeachers() {
        when(dao.findAll()).thenReturn(getTeachers());

        assertThat(service.findAll(), hasItem(teacherToTeacherDto(teacher)));
    }

    @Test
    void findAllShouldFindAllTeachersPageable() {
        Page page = new Page(0, 2);
        Pageable<Teacher> teacherPageable = new Pageable<>(getTeachers(), 0, 2);
        when(dao.findAll(page)).thenReturn(teacherPageable);

        Pageable<TeacherDto> teacherDtoPageable = new Pageable<>(getTeachers().stream().map(TeacherMapper::teacherToTeacherDto).collect(Collectors.toList()), 0, 2);
        assertThat(service.findAll(page), equalTo(teacherDtoPageable));
    }

    @Test
    void findByIdShouldFindByIdTeacher() {
        when(dao.findById(1L)).thenReturn(Optional.of(teacher));

        assertThat(service.findById(1L), is(not(emptyOptional())));
    }

    @Test
    void createTeacherShouldCreateTeacher() {
        Teacher teacher = Teacher.builder()
                .withFirstName("Mykhailo")
                .withLastName("Dymin")
                .withBirthDate(new Date(1000))
                .withAddress(Address.builder().withId(3L).build())
                .withPhoneNumber("123132512")
                .withEmail("Mykhailo@gmail.com")
                .withPassword(null)
                .withLinkedinUrl("linkedin.com/in/Mykhailo/")
                .build();
        service.registerTeacher(teacherToTeacherDto(teacher));

        verify(dao).create(teacher);
        verify(addressDao).createAndReturnId(Address.builder().withId(3L).build());
    }

    @Test
    void updateTeacherShouldUpdateTeacher() {
        service.updateTeacher(teacherToTeacherDto(teacher));

        verify(dao).update(teacher);
    }

    @Test
    void deleteTeacherByIdShouldDeleteTeacher() {
        Long id = 5L;
        service.deleteTeacherById(id);

        verify(dao).delete(id);
    }

    @Test
    void authenticateTeacherShouldReturnTeacherWhenEmailAndPasswordAreCorrect() {
        when(dao.findByEmail("Mykhailo@gmail.com")).thenReturn(Optional.of(teacher));
        when(passwordEncoder.matches("1111", "1111")).thenReturn(true);

        assertDoesNotThrow(() -> service.authenticateTeacher("Mykhailo@gmail.com", "1111"));
        assertThat(service.authenticateTeacher("Mykhailo@gmail.com", "1111"), equalTo(teacherToTeacherDto(teacher)));
    }

    @Test
    void authenticateTeacherShouldThrowServiceExceptionWhenPasswordIsWrong() {
        when(dao.findByEmail("Mykhailo@gmail.com")).thenReturn(Optional.of(teacher));
        when(passwordEncoder.matches("1111", "1111")).thenReturn(false);

        assertThatThrownBy(() -> service.authenticateTeacher("Mykhailo@gmail.com", "1111"))
                .isExactlyInstanceOf(ServiceException.class)
                .hasMessage("Wrong password");
    }

    @Test
    void authenticateTeacherShouldThrowServiceExceptionWhenTeacherIsNotFoundByEmail() {
        when(dao.findByEmail("Mykhailo@gmail.com")).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.authenticateTeacher("Mykhailo@gmail.com", "1111"))
                .isExactlyInstanceOf(ServiceException.class)
                .hasMessage("Email not found");
    }

    private List<Teacher> getTeachers() {
        List<Teacher> teachers = new ArrayList<>();
        teachers.add(Teacher.builder()
                .withId(1L)
                .withFirstName("Mykhailo")
                .withLastName("Dymin")
                .withBirthDate(new Date(1000))
                .withAddress(Address.builder().withId(3L).build())
                .withPhoneNumber("123132512")
                .withEmail("Mykhailo@gmail.com")
                .withPassword("1111")
                .withLinkedinUrl("linkedin.com/in/Mykhailo/")
                .build());
        teachers.add(Teacher.builder()
                .withId(2L)
                .withFirstName("Daniil")
                .withLastName("Danilov")
                .withBirthDate(new Date(1000))
                .withAddress(Address.builder().withId(3L).build())
                .withPhoneNumber("123132512")
                .withEmail("Mykhailo@gmail.com")
                .withPassword("1111")
                .withLinkedinUrl("linkedin.com/in/Daniil/")
                .build());
        return teachers;
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
