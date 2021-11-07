package org.foxminded.university.dao;

import org.foxminded.university.entity.Schedule;

import java.time.LocalDate;
import java.util.List;

public interface ScheduleDao extends AbstractDao<Long, Schedule> {

    List<Schedule> getScheduleForStudent(Long id);

    List<Schedule> getScheduleForTeacher(Long id);

    void insertDates(Long id, LocalDate localDate);

    List<LocalDate> getDates(Long id);
}
