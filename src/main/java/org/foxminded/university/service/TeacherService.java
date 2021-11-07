package org.foxminded.university.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.foxminded.university.dao.AddressDao;
import org.foxminded.university.dao.TeacherDao;
import org.foxminded.university.domain.Page;
import org.foxminded.university.domain.Pageable;
import org.foxminded.university.dto.TeacherDto;
import org.foxminded.university.entity.Address;
import org.foxminded.university.entity.Teacher;
import org.foxminded.university.exception.ServiceException;
import org.foxminded.university.mapper.TeacherMapper;
import org.foxminded.university.validator.PersonValidator;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.foxminded.university.mapper.AddressMapper.addressDtoToAddress;
import static org.foxminded.university.mapper.TeacherMapper.teacherDtoToTeacher;
import static org.foxminded.university.mapper.TeacherMapper.teacherToTeacherDto;

@Service
@AllArgsConstructor
@Slf4j
public class TeacherService {
    private final TeacherDao teacherDao;
    private final AddressDao addressDao;
    private final PersonValidator personValidator;
    private final PasswordEncoder passwordEncoder;

    public List<TeacherDto> findAll() {
        return teacherDao.findAll()
                .stream()
                .map(TeacherMapper::teacherToTeacherDto)
                .collect(Collectors.toList());
    }

    public Pageable<TeacherDto> findAll(Page page) {
        List<TeacherDto> teacherDtos = teacherDao.findAll(page).getItems()
                .stream()
                .map(TeacherMapper::teacherToTeacherDto)
                .collect(Collectors.toList());
        return new Pageable<>(teacherDtos, page.getPageNumber(), page.getItemsPerPage());
    }

    public TeacherDto findById(Long id) {
        Optional<Teacher> teacher = teacherDao.findById(id);
        if(teacher.isEmpty()){
            throw new ServiceException("Teacher not found with id: "+ id);
        }
        return teacherToTeacherDto(teacher.get());
    }

    public void registerTeacher(TeacherDto teacher) {
        if (teacherDao.findByEmail(teacher.getEmail()).isPresent()){
            throw new ServiceException("Teacher with such a email already exists");
        }
        Long addressId = addressDao.createAndReturnId(addressDtoToAddress(teacher.getAddress()));
        personValidator.personValidator(teacher);
        Teacher createdTeacher = Teacher.builder()
                .withFirstName(teacher.getFirstName())
                .withLastName(teacher.getLastName())
                .withBirthDate(teacher.getBirthDate())
                .withAddress(Address.builder().withId(addressId).build())
                .withPhoneNumber(teacher.getPhoneNumber())
                .withEmail(teacher.getEmail())
                .withPassword(passwordEncoder.encode(teacher.getPassword()))
                .withLinkedinUrl(teacher.getLinkedinUrl())
                .build();
        teacherDao.create(createdTeacher);
    }

    public void updateTeacher(TeacherDto teacher) {
        personValidator.personValidator(teacher);
        teacherDao.update(teacherDtoToTeacher(teacher));
    }

    public void deleteTeacherById(Long id) {
        teacherDao.delete(id);
    }

    public TeacherDto authenticateTeacher(String email, String password) {
        Optional<Teacher> teacher = teacherDao.findByEmail(email);
        if (teacher.isPresent()) {
            if (!passwordEncoder.matches(password, teacher.get().getPassword())) {
                throw new ServiceException("Wrong password");
            }
            return teacherToTeacherDto(teacher.get());
        } else {
            throw new ServiceException("Email not found");
        }
    }
}
