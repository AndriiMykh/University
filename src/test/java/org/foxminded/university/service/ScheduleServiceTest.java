package org.foxminded.university.service;

import org.foxminded.university.dao.ScheduleDao;
import org.foxminded.university.domain.Page;
import org.foxminded.university.domain.Pageable;
import org.foxminded.university.entity.Lesson;
import org.foxminded.university.entity.Schedule;
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

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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

        assertThat(service.findAll())
                .isNotEmpty()
                .contains(schedule);
    }

    @Test
    void findAllPageableShouldFindAllSchedulesPageable() {
        Page page = new Page(0, 2);
        Pageable<Schedule> schedulePageable = new Pageable<>(getSchedules(), 0,2);
        when(dao.findAll(page)).thenReturn(schedulePageable);

        assertThat(service.findAll(page))
                .isEqualTo(schedulePageable);
    }

    @Test
    void findByIdShouldFindScheduleById() {
        when(dao.findById(1L)).thenReturn(Optional.of(schedule));

        assertThat(service.findById(1L))
                .hasValue(schedule);
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

    private List<Schedule> getSchedules(){
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
