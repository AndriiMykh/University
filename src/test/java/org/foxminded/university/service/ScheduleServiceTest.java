package org.foxminded.university.service;

import org.foxminded.university.dao.ScheduleDao;
import org.foxminded.university.domain.Page;
import org.foxminded.university.domain.Pageable;
import org.foxminded.university.entity.Schedule;
import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.Time;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.internal.util.JavaEightUtil.emptyOptional;

@ExtendWith(MockitoExtension.class)
class ScheduleServiceTest {

    @Mock
    private ScheduleDao dao;

    @InjectMocks
    private ScheduleService service;

    private Schedule schedule = Schedule.builder()
            .withId(4L)
            .withDate(LocalDate.now())
            .withStartTime(new Time(1000))
            .withEndTime(new Time(2000))
            .build();

    @Test
    void findAllShouldFindAllSchedules() {
        when(dao.findAll()).thenReturn(getSchedules());

        assertThat(service.findAll(), hasItem(schedule));
    }

    @Test
    void findAllPageableShouldFindAllSchedulesPageable() {
        Page page = new Page(0, 2);
        Pageable<Schedule> schedulePageable = new Pageable<>(getSchedules(), 0, 2);
        when(dao.findAll(page)).thenReturn(schedulePageable);

        assertThat(service.findAll(page), equalTo(schedulePageable));
    }

    @Test
    void findByIdShouldFindScheduleById() {
        when(dao.findById(1L)).thenReturn(Optional.of(schedule));

        assertThat(service.findById(1L), is(not(emptyOptional())));
    }

    @Test
    void createScheduleShouldUseCreateMethod() {
        service.createSchedule(schedule);

        verify(dao).create(schedule);
    }

    @Test
    void updateScheduleShouldUseUpdateMethod() {
        service.updateSchedule(schedule);

        verify(dao).update(schedule);
    }

    @Test
    void deleteScheduleByIdShouldUseDeleteMethod() {
        Long id = 5L;
        service.deleteScheduleById(id);

        verify(dao).delete(id);
    }

    @Test
    void getScheduleForStudentForTodayShouldReturnScheduleForDay() {
        Long id = 5L;
        when(dao.getScheduleForStudent(id)).thenReturn(getSchedules());
        assertThat(service.getScheduleForStudentForToday(id), hasSize(2));
    }

    @Test
    void getScheduleForStudentForTodayShouldReturnScheduleForMonth() {
        Long id = 5L;
        when(dao.getScheduleForStudent(id)).thenReturn(getSchedules());
        assertThat(service.getScheduleForStudentForMonth(id), hasSize(2));
    }

    @Test
    void getScheduleForTodayShouldReturnScheduleForDay() {
        Long id = 5L;
        when(dao.getScheduleForTeacher(id)).thenReturn(getSchedules());
        assertThat(service.getScheduleForTeacherForToday(id), hasSize(2));
    }

    @Test
    void getScheduleForTodayShouldReturnScheduleForMonth() {
        Long id = 5L;
        when(dao.getScheduleForTeacher(id)).thenReturn(getSchedules());
        assertThat(service.getScheduleForTeacherForMonth(id), hasSize(2));
    }

    private List<Schedule> getSchedules() {
        List<Schedule> schedules = new ArrayList<>();
        schedules.add(Schedule.builder()
                .withId(4L)
                .withDate(LocalDate.now())
                .withStartTime(new Time(1000))
                .withEndTime(new Time(2000))
                .build());
        schedules.add(Schedule.builder()
                .withId(1L)
                .withDate(LocalDate.now())
                .withStartTime(new Time(3000))
                .withEndTime(new Time(4000))
                .build());
        return schedules;
    }
}
