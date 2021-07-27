package org.foxminded.university.mapper;

import org.foxminded.university.dto.AddressDto;
import org.foxminded.university.dto.GroupDto;
import org.foxminded.university.dto.StudentDto;
import org.foxminded.university.entity.Address;
import org.foxminded.university.entity.Group;
import org.foxminded.university.entity.Student;
import org.junit.jupiter.api.Test;

import java.sql.Date;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.foxminded.university.entity.StudiesType.FULL_TIME;
import static org.foxminded.university.mapper.StudentMapper.studentDtoToStudent;
import static org.foxminded.university.mapper.StudentMapper.studentToStudentDto;
import static org.junit.jupiter.api.Assertions.assertAll;

public class StudentMapperTest {

    @Test
    public void studentToStudentDtoShouldConvertStudentToStudentDto() {
        Student student = Student.builder()
                .withId(3L)
                .withFirstName("Kyrylo")
                .withLastName("Dymin")
                .withBirthDate(new Date(1000))
                .withAddress(Address.builder().build())
                .withPhoneNumber("123132512")
                .withEmail("Kyrylo@gmail.com")
                .withPassword("1111")
                .withGroup(Group.builder().build())
                .withStudiesType(FULL_TIME)
                .build();
        StudentDto studentDto = studentToStudentDto(student);
        assertAll(
                () -> assertThat(studentDto.getId()).isEqualTo(student.getId()),
                () -> assertThat(studentDto.getFirstName()).isEqualTo(student.getFirstName()),
                () -> assertThat(studentDto.getLastName()).isEqualTo(student.getLastName()),
                () -> assertThat(studentDto.getBirthDate()).isEqualTo(student.getBirthDate()),
                () -> assertThat(studentDto.getAddress()).isEqualTo(AddressDto.builder().build()),
                () -> assertThat(studentDto.getPhoneNumber()).isEqualTo(student.getPhoneNumber()),
                () -> assertThat(studentDto.getEmail()).isEqualTo(student.getEmail()),
                () -> assertThat(studentDto.getPassword()).isEqualTo(student.getPassword()),
                () -> assertThat(studentDto.getGroup()).isEqualTo(GroupDto.builder().build()),
                () -> assertThat(studentDto.getStudiesType()).isEqualTo(student.getStudiesType())
        );
    }

    @Test
    public void studentToStudentDtoShouldReturnEmptyStudentDto(){
        assertThat(studentToStudentDto(null)).isEqualTo(StudentDto.builder().build());
    }

    @Test
    public void studentDtoToStudentShouldConvertStudentDtoToStudent() {
        StudentDto studentDto = StudentDto.builder()
                .withId(3L)
                .withFirstName("Kyrylo")
                .withLastName("Dymin")
                .withBirthDate(new Date(1000))
                .withAddress(AddressDto.builder().build())
                .withPhoneNumber("123132512")
                .withEmail("Kyrylo@gmail.com")
                .withPassword("1111")
                .withGroup(GroupDto.builder().build())
                .withStudiesType(FULL_TIME)
                .build();
        Student student = studentDtoToStudent(studentDto);
        assertAll(
                () -> assertThat(student.getId()).isEqualTo(studentDto.getId()),
                () -> assertThat(student.getFirstName()).isEqualTo(studentDto.getFirstName()),
                () -> assertThat(student.getLastName()).isEqualTo(studentDto.getLastName()),
                () -> assertThat(student.getBirthDate()).isEqualTo(studentDto.getBirthDate()),
                () -> assertThat(student.getAddress()).isEqualTo(Address.builder().build()),
                () -> assertThat(student.getPhoneNumber()).isEqualTo(studentDto.getPhoneNumber()),
                () -> assertThat(student.getEmail()).isEqualTo(studentDto.getEmail()),
                () -> assertThat(student.getPassword()).isEqualTo(studentDto.getPassword()),
                () -> assertThat(student.getGroup()).isEqualTo(Group.builder().build()),
                () -> assertThat(student.getStudiesType()).isEqualTo(studentDto.getStudiesType())
        );
    }

    @Test
    public void studentDtoToStudentShouldReturnEmptyStudent(){
        assertThat(studentDtoToStudent(null)).isEqualTo(Student.builder().build());
    }
}
