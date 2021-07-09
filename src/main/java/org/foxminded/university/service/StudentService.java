package org.foxminded.university.service;

import lombok.AllArgsConstructor;
import org.foxminded.university.dao.StudentDao;
import org.foxminded.university.domain.Page;
import org.foxminded.university.domain.Pageable;
import org.foxminded.university.entity.Schedule;
import org.foxminded.university.entity.Student;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class StudentService {
    private final StudentDao studentDao;

    public List<Student> findAll(){
        return studentDao.findAll();
    }

    public Pageable<Student> findAll(Page page){
        return studentDao.findAll(page);
    }

    public Optional<Student> findById(Long id){
        return studentDao.findById(id);
    }

    public void createStudent(Student student){
        studentDao.create(student);
    }

    public void updateStudent(Student student){
        studentDao.update(student);
    }

    public void deleteStudentById(Long id){
        studentDao.delete(id);
    }

    public List<Schedule> getScheduleForToday(Long id){
        List<Schedule> schedules = studentDao.getScheduleForStudent(id);
        return schedules.stream()
                .filter(schedule -> schedule.getDate().isEqual(LocalDate.now()))
                .collect(Collectors.toList());
    }

    public List<Schedule> getScheduleForMonth( Long id){
        List<Schedule> schedules = studentDao.getScheduleForStudent(id);
        return schedules.stream()
                .filter(schedule -> schedule.getDate().isBefore(LocalDate.now().plusMonths(1)))
                .collect(Collectors.toList());
    }
}
