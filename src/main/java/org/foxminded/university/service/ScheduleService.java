package org.foxminded.university.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.foxminded.university.dao.ScheduleDao;
import org.foxminded.university.domain.Page;
import org.foxminded.university.domain.Pageable;
import org.foxminded.university.entity.Schedule;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Slf4j
public class ScheduleService {
    private final ScheduleDao scheduleDao;

    public List<Schedule> findAll() {
        return scheduleDao.findAll();
    }

    public Pageable<Schedule> findAll(Page page) {
        return scheduleDao.findAll(page);
    }

    public Optional<Schedule> findById(Long id) {
        return scheduleDao.findById(id);
    }

    public void createSchedule(Schedule schedule) {
        scheduleDao.create(schedule);
    }

    public void updateSchedule(Schedule schedule) {
        scheduleDao.update(schedule);
    }

    public void deleteScheduleById(Long id) {
        scheduleDao.delete(id);
    }

    public List<Schedule> getScheduleForStudentForToday(Long id) {
        List<Schedule> schedules = scheduleDao.getScheduleForStudent(id);
        return getScheduleForToday(schedules);
    }

    public List<Schedule> getScheduleForStudentForMonth(Long id) {
        List<Schedule> schedules = scheduleDao.getScheduleForStudent(id);
        return getScheduleForMonth(schedules);
    }


    public List<Schedule> getScheduleForTeacherForToday(Long id) {
        List<Schedule> schedules = scheduleDao.getScheduleForTeacher(id);
        return getScheduleForToday(schedules);
    }

    public List<Schedule> getScheduleForTeacherForMonth(Long id) {
        List<Schedule> schedules = scheduleDao.getScheduleForTeacher(id);
        return getScheduleForMonth(schedules);
    }

    private List<Schedule> getScheduleForToday(List<Schedule> schedules) {
        return schedules.stream()
                .filter(schedule -> schedule.getDate().isEqual(LocalDate.now()))
                .collect(Collectors.toList());
    }

    private List<Schedule> getScheduleForMonth(List<Schedule> schedules) {
        return schedules.stream()
                .filter(schedule -> schedule.getDate().isBefore(LocalDate.now().plusMonths(1)))
                .collect(Collectors.toList());
    }
}
