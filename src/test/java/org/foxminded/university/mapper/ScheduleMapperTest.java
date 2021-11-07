package org.foxminded.university.mapper;

import org.foxminded.university.dto.ScheduleDto;
import org.foxminded.university.entity.Schedule;
import org.junit.jupiter.api.Test;

import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.foxminded.university.mapper.ScheduleMapper.scheduleDtoToSchedule;
import static org.foxminded.university.mapper.ScheduleMapper.scheduleToScheduleDto;
import static org.junit.jupiter.api.Assertions.assertAll;

public class ScheduleMapperTest {

    @Test
    public void scheduleToScheduleDtoShouldConvertScheduleToScheduleDto() {
        Schedule schedule = Schedule.builder()
                .withId(1L)
                .withDate(LocalDate.now())
                .withStartTime(Time.valueOf(LocalTime.now()))
                .withEndTime(Time.valueOf(LocalTime.now()))
                .build();
        ScheduleDto scheduleDto = scheduleToScheduleDto(schedule);
        assertAll(
                () -> assertThat(scheduleDto.getId()).isEqualTo(schedule.getId()),
                () -> assertThat(scheduleDto.getDate()).isEqualTo(schedule.getDate()),
                () -> assertThat(scheduleDto.getStartTime()).isEqualTo(schedule.getStartTime()),
                () -> assertThat(scheduleDto.getEndTime()).isEqualTo(schedule.getEndTime())
        );
    }

    @Test
    public void scheduleToScheduleDtoShouldReturnEmptyScheduleDto() {
        assertThat(scheduleToScheduleDto(null)).isEqualTo(ScheduleDto.builder().build());
    }

    @Test
    public void lessonDtoToScheduleShouldConvertScheduleDtoToSchedule() {
        ScheduleDto scheduleDto = ScheduleDto.builder()
                .withId(1L)
                .withDate(LocalDate.now())
                .withStartTime(Time.valueOf(LocalTime.now()))
                .withEndTime(Time.valueOf(LocalTime.now()))
                .build();
        Schedule schedule = scheduleDtoToSchedule(scheduleDto);
        assertAll(
                () -> assertThat(schedule.getId()).isEqualTo(scheduleDto.getId()),
                () -> assertThat(schedule.getDate()).isEqualTo(scheduleDto.getDate()),
                () -> assertThat(schedule.getStartTime()).isEqualTo(scheduleDto.getStartTime()),
                () -> assertThat(schedule.getEndTime()).isEqualTo(scheduleDto.getEndTime())
        );
    }

    @Test
    public void scheduleDtoToScheduleShouldReturnEmptySchedule() {
        assertThat(scheduleDtoToSchedule(null)).isEqualTo(Schedule.builder().build());
    }
}
