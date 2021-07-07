package org.foxminded.university.service;

import org.foxminded.university.dao.ScheduleDao;
import org.foxminded.university.domain.Page;
import org.foxminded.university.domain.Pageable;
import org.foxminded.university.entity.Schedule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ScheduleService {
    private ScheduleDao scheduleDao;

    @Autowired
    public ScheduleService(ScheduleDao scheduleDao) {
        this.scheduleDao = scheduleDao;
    }

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
}
