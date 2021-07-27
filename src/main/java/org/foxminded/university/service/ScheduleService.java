package org.foxminded.university.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.foxminded.university.dao.ScheduleDao;
import org.foxminded.university.domain.Page;
import org.foxminded.university.domain.Pageable;
import org.foxminded.university.dto.ScheduleDto;
import org.foxminded.university.entity.Schedule;
import org.foxminded.university.exception.ServiceException;
import org.foxminded.university.mapper.ScheduleMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.foxminded.university.mapper.ScheduleMapper.scheduleDtoToSchedule;
import static org.foxminded.university.mapper.ScheduleMapper.scheduleToScheduleDto;

@Service
@AllArgsConstructor
@Slf4j
public class ScheduleService {
    private final ScheduleDao scheduleDao;

    public List<ScheduleDto> findAll() {
        return scheduleDao.findAll()
                .stream()
                .map(ScheduleMapper::scheduleToScheduleDto)
                .collect(Collectors.toList());
    }

    public Pageable<ScheduleDto> findAll(Page page) {
        List<ScheduleDto> scheduleDtos = scheduleDao.findAll(page).getItems()
                .stream()
                .map(ScheduleMapper::scheduleToScheduleDto)
                .collect(Collectors.toList());
        return new Pageable<>(scheduleDtos, page.getPageNumber(), page.getItemsPerPage());
    }

    public ScheduleDto findById(Long id) {
        Optional<Schedule> schedule = scheduleDao.findById(id);
        if (schedule.isEmpty()) {
            throw new ServiceException("Schedule not found with id: " + id);
        }
        return scheduleToScheduleDto(schedule.get());
    }

    public void createSchedule(ScheduleDto schedule) {
        scheduleDao.create(scheduleDtoToSchedule(schedule));
    }

    public void updateSchedule(ScheduleDto schedule) {
        scheduleDao.update(scheduleDtoToSchedule(schedule));
    }

    public void deleteScheduleById(Long id) {
        scheduleDao.delete(id);
    }

    public List<ScheduleDto> getScheduleForStudentForToday(Long id) {
        List<Schedule> schedules = scheduleDao.getScheduleForStudent(id);
        return getScheduleForToday(schedules)
                .stream()
                .map(ScheduleMapper::scheduleToScheduleDto)
                .collect(Collectors.toList());
    }

    public List<ScheduleDto> getScheduleForStudentForMonth(Long id) {
        List<Schedule> schedules = scheduleDao.getScheduleForStudent(id);
        return getScheduleForMonth(schedules)
                .stream()
                .map(ScheduleMapper::scheduleToScheduleDto)
                .collect(Collectors.toList());
    }


    public List<ScheduleDto> getScheduleForTeacherForToday(Long id) {
        List<Schedule> schedules = scheduleDao.getScheduleForTeacher(id);
        return getScheduleForToday(schedules)
                .stream()
                .map(ScheduleMapper::scheduleToScheduleDto)
                .collect(Collectors.toList());
    }

    public List<ScheduleDto> getScheduleForTeacherForMonth(Long id) {
        List<Schedule> schedules = scheduleDao.getScheduleForTeacher(id);
        return getScheduleForMonth(schedules)
                .stream()
                .map(ScheduleMapper::scheduleToScheduleDto)
                .collect(Collectors.toList());
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
