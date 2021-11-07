package org.foxminded.university.mapper;

import org.foxminded.university.dto.TeacherDto;
import org.foxminded.university.entity.Teacher;

import static org.foxminded.university.mapper.AddressMapper.addressDtoToAddress;
import static org.foxminded.university.mapper.AddressMapper.addressToAddressDto;

public class TeacherMapper {
    public static TeacherDto teacherToTeacherDto(Teacher teacher){
        if (teacher != null) {
            TeacherDto teacherDto = TeacherDto.builder()
                    .withId(teacher.getId())
                    .withAddress(addressToAddressDto(teacher.getAddress()))
                    .withBirthDate(teacher.getBirthDate())
                    .withEmail(teacher.getEmail())
                    .withFirstName(teacher.getFirstName())
                    .withLastName(teacher.getLastName())
                    .withPassword(teacher.getPassword())
                    .withPhoneNumber(teacher.getPhoneNumber())
                    .withLinkedinUrl(teacher.getLinkedinUrl())
                    .build();
            return teacherDto;
        }else {
            return TeacherDto.builder().build();
        }
    }

    public static Teacher teacherDtoToTeacher(TeacherDto teacherDto){
        if (teacherDto != null) {
            Teacher teacher = Teacher.builder()
                    .withId(teacherDto.getId())
                    .withAddress(addressDtoToAddress(teacherDto.getAddress()))
                    .withBirthDate(teacherDto.getBirthDate())
                    .withEmail(teacherDto.getEmail())
                    .withFirstName(teacherDto.getFirstName())
                    .withLastName(teacherDto.getLastName())
                    .withPassword(teacherDto.getPassword())
                    .withPhoneNumber(teacherDto.getPhoneNumber())
                    .withLinkedinUrl(teacherDto.getLinkedinUrl())
                    .build();
            return teacher;
        }else {
            return Teacher.builder().build();
        }
    }
}
