package org.foxminded.university.mapper;

import org.foxminded.university.dto.ScheduleDto;
import org.foxminded.university.entity.Schedule;

public class ScheduleMapper {
    public static Schedule scheduleDtoToSchedule(ScheduleDto scheduleDto){
        if(scheduleDto != null){
            Schedule schedule = Schedule.builder()
                    .withId(scheduleDto.getId())
                    .withDate(scheduleDto.getDate())
                    .withStartTime(scheduleDto.getStartTime())
                    .withEndTime(scheduleDto.getEndTime())
                    .build();
            return schedule;
        }else{
            return Schedule.builder().build();
        }
    }

    public static ScheduleDto scheduleToScheduleDto(Schedule schedule){
        if(schedule != null){
            ScheduleDto scheduleDto = ScheduleDto.builder()
                    .withId(schedule.getId())
                    .withDate(schedule.getDate())
                    .withStartTime(schedule.getStartTime())
                    .withEndTime(schedule.getEndTime())
                    .build();
            return scheduleDto;
        }else{
            return ScheduleDto.builder().build();
        }
    }
}
