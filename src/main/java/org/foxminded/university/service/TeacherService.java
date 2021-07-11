package org.foxminded.university.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.foxminded.university.dao.TeacherDao;
import org.foxminded.university.domain.Page;
import org.foxminded.university.domain.Pageable;
import org.foxminded.university.entity.Teacher;
import org.foxminded.university.exception.ServiceException;
import org.foxminded.university.validator.PersonValidator;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
@Slf4j
public class TeacherService {
    private final TeacherDao teacherDao;
    private final PersonValidator personValidator;
    private final PasswordEncoder passwordEncoder;

    public List<Teacher> findAll() {
        return teacherDao.findAll();
    }

    public Pageable<Teacher> findAll(Page page) {
        return teacherDao.findAll(page);
    }

    public Optional<Teacher> findById(Long id) {
        return teacherDao.findById(id);
    }

    public void createTeacher(Teacher teacher) {
        personValidator.personValidator(teacher);
        Teacher createdTeacher = Teacher.builder()
                .withFirstName(teacher.getFirstName())
                .withLastName(teacher.getLastName())
                .withBirthDate(teacher.getBirthDate())
                .withAddress(teacher.getAddress())
                .withPhoneNumber(teacher.getPhoneNumber())
                .withEmail(teacher.getEmail())
                .withPassword(passwordEncoder.encode(teacher.getPassword()))
                .withLinkedinUrl(teacher.getLinkedinUrl())
                .build();
        teacherDao.create(createdTeacher);
    }

    public void updateTeacher(Teacher teacher) {
        personValidator.personValidator(teacher);
        teacherDao.update(teacher);
    }

    public void deleteTeacherById(Long id) {
        teacherDao.delete(id);
    }

    public Teacher authenticateTeacher(String email, String password) {
        Optional<Teacher> teacher = teacherDao.findByEmail(email);
        if (teacher.isPresent()) {
            if (!passwordEncoder.matches(password, teacher.get().getPassword())) {
                throw new ServiceException("Wrong password");
            }
            return teacher.get();
        } else {
            throw new ServiceException("Email not found");
        }
    }
}
