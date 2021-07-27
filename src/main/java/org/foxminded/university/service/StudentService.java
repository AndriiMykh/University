package org.foxminded.university.service;

import lombok.AllArgsConstructor;
import org.foxminded.university.dao.AddressDao;
import org.foxminded.university.dao.StudentDao;
import org.foxminded.university.domain.Page;
import org.foxminded.university.domain.Pageable;
import org.foxminded.university.dto.StudentDto;
import org.foxminded.university.entity.Address;
import org.foxminded.university.entity.Student;
import org.foxminded.university.exception.ServiceException;
import org.foxminded.university.mapper.StudentMapper;
import org.foxminded.university.validator.PersonValidator;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.foxminded.university.mapper.AddressMapper.addressDtoToAddress;
import static org.foxminded.university.mapper.GroupMapper.groupDtoToGroup;
import static org.foxminded.university.mapper.StudentMapper.studentDtoToStudent;
import static org.foxminded.university.mapper.StudentMapper.studentToStudentDto;


@Service
@AllArgsConstructor
public class StudentService {
    private final StudentDao studentDao;
    private final AddressDao addressDao;
    private final PersonValidator personValidator;
    private final PasswordEncoder passwordEncoder;

    public List<StudentDto> findAll() {
        return studentDao.findAll()
                .stream()
                .map(StudentMapper::studentToStudentDto)
                .collect(Collectors.toList());
    }

    public Pageable<StudentDto> findAll(Page page) {
        List<StudentDto> studentDtos = studentDao.findAll(page).getItems()
                .stream()
                .map(StudentMapper::studentToStudentDto)
                .collect(Collectors.toList());
        return new Pageable<>(studentDtos, page.getPageNumber(), page.getItemsPerPage());
    }

    public StudentDto findById(Long id) {
        Optional<Student> student = studentDao.findById(id);
        if(student.isEmpty()){
            throw new ServiceException("Student not found with id: "+ id);
        }
        return studentToStudentDto(student.get());
    }

    public void registerStudent(StudentDto student) {
        System.out.println(student);
        Long addressId = addressDao.createAndReturnId(addressDtoToAddress(student.getAddress()));
        personValidator.personValidator(student);
        Student createdStudent = Student.builder()
                .withFirstName(student.getFirstName())
                .withLastName(student.getLastName())
                .withBirthDate(student.getBirthDate())
                .withAddress(Address.builder().withId(addressId).build())
                .withPhoneNumber(student.getPhoneNumber())
                .withEmail(student.getEmail())
                .withPassword(passwordEncoder.encode(student.getPassword()))
                .withGroup(groupDtoToGroup(student.getGroup()))
                .withStudiesType(student.getStudiesType())
                .build();
        studentDao.create(createdStudent);
    }

    public void updateStudent(StudentDto student) {
        personValidator.personValidator(student);
        studentDao.update(studentDtoToStudent(student));
    }

    public void deleteStudentById(Long id) {
        studentDao.delete(id);
    }

    public StudentDto authenticateStudent(String email, String password) {
        Optional<Student> student = studentDao.findByEmail(email);
        if (student.isPresent()) {
            if (!passwordEncoder.matches(password, student.get().getPassword())) {
                throw new ServiceException("Wrong password");
            }
            return studentToStudentDto(student.get());
        } else {
            throw new ServiceException("Email not found");
        }
    }
}
