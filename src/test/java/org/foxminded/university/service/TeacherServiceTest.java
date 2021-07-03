package org.foxminded.university.service;

import org.foxminded.university.dao.TeacherDao;
import org.foxminded.university.domain.Page;
import org.foxminded.university.domain.Pageable;
import org.foxminded.university.entity.Address;
import org.foxminded.university.entity.Schedule;
import org.foxminded.university.entity.Teacher;
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
class TeacherServiceTest {
    @Mock
    private  TeacherDao dao;
    @InjectMocks
    private TeacherService service;

    private final Teacher teacher = Teacher.builder()
            .withId(1L)
            .withFirstName("Mykhailo")
            .withLastName("Dymin")
            .withBirthDate(new Date(1000))
            .withAddress( Address.builder().withId(3L).build())
            .withPhoneNumber("123132512")
            .withEmail("Mykhailo@gmail.com")
            .withPassword("1111")
            .withLinkedinUrl("linkedin.com/in/Mykhailo/")
            .build();
    @Test
    void findAllShouldFindAllTeachers() {
        when(dao.findAll()).thenReturn(getTeachers());

        assertThat(service.findAll())
                .isNotEmpty()
                .contains(teacher);
    }

    @Test
    void findAllShouldFindAllTeachersPageable() {
        Page page = new Page(0, 2);
        Pageable<Teacher> teacherPageable = new Pageable<>(getTeachers(), 0,2);
        when(dao.findAll(page)).thenReturn(teacherPageable);

        assertThat(service.findAll(page))
                .isEqualTo(teacherPageable);
    }

    @Test
    void findByIdShouldFindByIdTeacher() {
        when(dao.findById(1L)).thenReturn(Optional.of(teacher));

        assertThat(service.findById(1L))
                .hasValue(teacher);
    }

    @Test
    void createTeacherShouldCreateTeacher() {
        service.createTeacher(teacher);

        verify(dao).create(teacher);
    }

    @Test
    void updateTeacherShouldUpdateTeacher() {
        service.updateTeacher(teacher);

        verify(dao).update(teacher);
    }

    @Test
    void deleteTeacherByIdShouldDeleteTeacher() {
        Long id = 5L;
        service.deleteTeacherById(id);

        verify(dao).delete(id);
    }

    @Test
    void getScheduleForTodayShouldReturnScheduleForDay(){
        Long id = 5L;
        when(dao.getScheduleForTeacher(id)).thenReturn(getSchedules());
        assertThat(service.getScheduleForToday(id))
                .hasSize(2);
    }

    @Test
    void getScheduleForTodayShouldReturnScheduleForMonth(){
        Long id = 5L;
        when(dao.getScheduleForTeacher(id)).thenReturn(getSchedules());
        assertThat(service.getScheduleForMonth(id))
                .hasSize(3);
    }

    private List<Teacher> getTeachers() {
        List<Teacher> teachers = new ArrayList<>();
        teachers.add(Teacher.builder()
                .withId(1L)
                .withFirstName("Mykhailo")
                .withLastName("Dymin")
                .withBirthDate(new Date(1000))
                .withAddress( Address.builder().withId(3L).build())
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
                .withAddress( Address.builder().withId(3L).build())
                .withPhoneNumber("123132512")
                .withEmail("Mykhailo@gmail.com")
                .withPassword("1111")
                .withLinkedinUrl("linkedin.com/in/Daniil/")
                .build());
        return teachers;
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
