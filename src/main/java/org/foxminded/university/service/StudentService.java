package org.foxminded.university.service;

import lombok.AllArgsConstructor;
import org.foxminded.university.dao.StudentDao;
import org.foxminded.university.domain.Page;
import org.foxminded.university.domain.Pageable;
import org.foxminded.university.entity.Student;
import org.foxminded.university.exception.ServiceException;
import org.foxminded.university.validator.PersonValidator;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
@AllArgsConstructor
public class StudentService {
    private final StudentDao studentDao;
    private final PersonValidator personValidator;
    private final PasswordEncoder passwordEncoder;

    public List<Student> findAll() {
        return studentDao.findAll();
    }

    public Pageable<Student> findAll(Page page) {
        return studentDao.findAll(page);
    }

    public Optional<Student> findById(Long id) {
        return studentDao.findById(id);
    }

    public void registerStudent(Student student) {
        personValidator.personValidator(student);
        Student createdStudent = Student.builder()
                .withFirstName(student.getFirstName())
                .withLastName(student.getLastName())
                .withBirthDate(student.getBirthDate())
                .withAddress(student.getAddress())
                .withPhoneNumber(student.getPhoneNumber())
                .withEmail(student.getEmail())
                .withPassword(passwordEncoder.encode(student.getPassword()))
                .withGroup(student.getGroup())
                .withStudiesType(student.getStudiesType())
                .build();
        studentDao.create(createdStudent);
    }

    public void updateStudent(Student student) {
        personValidator.personValidator(student);
        studentDao.update(student);
    }

    public void deleteStudentById(Long id) {
        studentDao.delete(id);
    }

    public Student authenticateStudent(String email, String password) {
        Optional<Student> student = studentDao.findByEmail(email);
        if (student.isPresent()) {
            if (!passwordEncoder.matches(password, student.get().getPassword())) {
                throw new ServiceException("Wrong password");
            }
            return student.get();
        } else {
            throw new ServiceException("Email not found");
        }
    }
}
