package org.foxminded.university.service;

import lombok.AllArgsConstructor;
import org.foxminded.university.dao.TeacherDao;
import org.foxminded.university.domain.Page;
import org.foxminded.university.domain.Pageable;
import org.foxminded.university.entity.Schedule;
import org.foxminded.university.entity.Teacher;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class TeacherService {
    private final TeacherDao teacherDao;

    public List<Teacher> findAll(){
        return teacherDao.findAll();
    }

    public Pageable<Teacher> findAll(Page page){
        return teacherDao.findAll(page);
    }

    public Optional<Teacher> findById(Long id){
        return teacherDao.findById(id);
    }

    public void createTeacher(Teacher teacher){
        teacherDao.create(teacher);
    }

    public void updateTeacher(Teacher teacher){
        teacherDao.update(teacher);
    }

    public void deleteTeacherById(Long id){
        teacherDao.delete(id);
    }

    public List<Schedule> getScheduleForToday(Long id){
        List<Schedule> schedules = teacherDao.getScheduleForTeacher(id);
        return schedules.stream()
                .filter(schedule -> schedule.getDate().isEqual(LocalDate.now()))
                .collect(Collectors.toList());
    }

    public List<Schedule> getScheduleForMonth( Long id){
        List<Schedule> schedules = teacherDao.getScheduleForTeacher(id);
        return schedules.stream()
                .filter(schedule -> schedule.getDate().isBefore(LocalDate.now().plusMonths(1)))
                .collect(Collectors.toList());
    }
}
