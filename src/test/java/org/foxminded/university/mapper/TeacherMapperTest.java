package org.foxminded.university.mapper;

import org.foxminded.university.dto.AddressDto;
import org.foxminded.university.dto.TeacherDto;
import org.foxminded.university.entity.Address;
import org.foxminded.university.entity.Teacher;
import org.junit.jupiter.api.Test;

import java.sql.Date;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.foxminded.university.mapper.TeacherMapper.teacherDtoToTeacher;
import static org.foxminded.university.mapper.TeacherMapper.teacherToTeacherDto;
import static org.junit.jupiter.api.Assertions.assertAll;

public class TeacherMapperTest {

    @Test
    public void teacherToTeacherDtoShouldConvertTeacherToTeacherDto() {
        Teacher teacher = Teacher.builder()
                .withId(3L)
                .withFirstName("Kyrylo")
                .withLastName("Dymin")
                .withBirthDate(new Date(1000))
                .withAddress(Address.builder().build())
                .withPhoneNumber("123132512")
                .withEmail("Kyrylo@gmail.com")
                .withPassword("1111")
                .withLinkedinUrl("some link")
                .build();
        TeacherDto teacherDto = teacherToTeacherDto(teacher);
        assertAll(
                () -> assertThat(teacherDto.getId()).isEqualTo(teacher.getId()),
                () -> assertThat(teacherDto.getFirstName()).isEqualTo(teacher.getFirstName()),
                () -> assertThat(teacherDto.getLastName()).isEqualTo(teacher.getLastName()),
                () -> assertThat(teacherDto.getBirthDate()).isEqualTo(teacher.getBirthDate()),
                () -> assertThat(teacherDto.getAddress()).isEqualTo(AddressDto.builder().build()),
                () -> assertThat(teacherDto.getPhoneNumber()).isEqualTo(teacher.getPhoneNumber()),
                () -> assertThat(teacherDto.getEmail()).isEqualTo(teacher.getEmail()),
                () -> assertThat(teacherDto.getPassword()).isEqualTo(teacher.getPassword()),
                () -> assertThat(teacherDto.getLinkedinUrl()).isEqualTo(teacher.getLinkedinUrl())
        );
    }

    @Test
    public void teacherToTeacherDtoShouldReturnEmptyTeacherDto(){
        assertThat(teacherToTeacherDto(null)).isEqualTo(TeacherDto.builder().build());
    }

    @Test
    public void teacherDtoToTeacherShouldConvertTeacherDtoToTeacher() {
        TeacherDto teacherDto = TeacherDto.builder()
                .withId(3L)
                .withFirstName("Kyrylo")
                .withLastName("Dymin")
                .withBirthDate(new Date(1000))
                .withAddress(AddressDto.builder().build())
                .withPhoneNumber("123132512")
                .withEmail("Kyrylo@gmail.com")
                .withPassword("1111")
                .withLinkedinUrl("some link")
                .build();
        Teacher teacher = teacherDtoToTeacher(teacherDto);
        assertAll(
                () -> assertThat(teacher.getId()).isEqualTo(teacherDto.getId()),
                () -> assertThat(teacher.getFirstName()).isEqualTo(teacherDto.getFirstName()),
                () -> assertThat(teacher.getLastName()).isEqualTo(teacherDto.getLastName()),
                () -> assertThat(teacher.getBirthDate()).isEqualTo(teacherDto.getBirthDate()),
                () -> assertThat(teacher.getAddress()).isEqualTo(Address.builder().build()),
                () -> assertThat(teacher.getPhoneNumber()).isEqualTo(teacherDto.getPhoneNumber()),
                () -> assertThat(teacher.getEmail()).isEqualTo(teacherDto.getEmail()),
                () -> assertThat(teacher.getPassword()).isEqualTo(teacherDto.getPassword()),
                () -> assertThat(teacher.getLinkedinUrl()).isEqualTo(teacherDto.getLinkedinUrl())
        );
    }

    @Test
    public void teacherDtoToTeacherShouldReturnEmptyTeacher(){
        assertThat(teacherDtoToTeacher(null)).isEqualTo(Teacher.builder().build());
    }
}
