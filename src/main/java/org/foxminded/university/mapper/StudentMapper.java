package org.foxminded.university.mapper;

import org.foxminded.university.dto.StudentDto;
import org.foxminded.university.entity.Student;

import static org.foxminded.university.mapper.AddressMapper.addressDtoToAddress;
import static org.foxminded.university.mapper.AddressMapper.addressToAddressDto;
import static org.foxminded.university.mapper.GroupMapper.groupDtoToGroup;
import static org.foxminded.university.mapper.GroupMapper.groupToGroupDto;


public class StudentMapper {
    public static StudentDto studentToStudentDto(Student student){
        if (student != null) {
            StudentDto studentDto = StudentDto.builder()
                    .withId(student.getId())
                    .withAddress(addressToAddressDto(student.getAddress()))
                    .withBirthDate(student.getBirthDate())
                    .withEmail(student.getEmail())
                    .withFirstName(student.getFirstName())
                    .withLastName(student.getLastName())
                    .withGroup(groupToGroupDto(student.getGroup()))
                    .withPassword(student.getPassword())
                    .withPhoneNumber(student.getPhoneNumber())
                    .withStudiesType(student.getStudiesType())
                    .build();
            return studentDto;
        }else {
            return StudentDto.builder().build();
        }
    }

    public static Student studentDtoToStudent(StudentDto studentDto){
        if (studentDto != null) {
            Student student = Student.builder()
                    .withId(studentDto.getId())
                    .withAddress(addressDtoToAddress(studentDto.getAddress()))
                    .withBirthDate(studentDto.getBirthDate())
                    .withEmail(studentDto.getEmail())
                    .withFirstName(studentDto.getFirstName())
                    .withLastName(studentDto.getLastName())
                    .withGroup(groupDtoToGroup(studentDto.getGroup()))
                    .withPassword(studentDto.getPassword())
                    .withPhoneNumber(studentDto.getPhoneNumber())
                    .withStudiesType(studentDto.getStudiesType())
                    .build();
            return student;
        }else {
            return Student.builder().build();
        }
    }
}
