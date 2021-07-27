package org.foxminded.university.mapper;

import org.foxminded.university.dto.GroupDto;
import org.foxminded.university.dto.StudentDto;
import org.foxminded.university.entity.Group;
import org.foxminded.university.entity.Student;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.foxminded.university.mapper.GroupMapper.groupDtoToGroup;
import static org.foxminded.university.mapper.GroupMapper.groupToGroupDto;
import static org.junit.jupiter.api.Assertions.assertAll;

public class GroupMapperTest {

    @Test
    public void groupToGroupDtoShouldConvertGroupToGroupDto() {
        Group group = Group.builder()
                .withId(1L)
                .withName("ds-41")
                .withAvailablePlaces(15)
                .withStudents(List.of(Student.builder().build()))
                .build();
        GroupDto groupDto = groupToGroupDto(group);
        assertAll(
                () -> assertThat(groupDto.getId()).isEqualTo(group.getId()),
                () -> assertThat(groupDto.getName()).isEqualTo(group.getName()),
                () -> assertThat(groupDto.getAvailablePlaces()).isEqualTo(group.getAvailablePlaces()),
                () -> assertThat(groupDto.getStudents()).isEqualTo(List.of(StudentDto.builder().build()))
        );
    }

    @Test
    public void groupToGroupDtoShouldReturnEmptyDtoGroup() {
        assertThat(groupToGroupDto(null)).isEqualTo(GroupDto.builder().build());
    }

    @Test
    public void groupDtoToGroupShouldConvertGroupDtoToGroup() {
        GroupDto groupDto = GroupDto.builder()
                .withId(1L)
                .withName("ds-41")
                .withAvailablePlaces(15)
                .withStudents(List.of(StudentDto.builder().build()))
                .build();
        Group group = groupDtoToGroup(groupDto);
        assertAll(
                () -> assertThat(group.getId()).isEqualTo(groupDto.getId()),
                () -> assertThat(group.getName()).isEqualTo(groupDto.getName()),
                () -> assertThat(group.getAvailablePlaces()).isEqualTo(groupDto.getAvailablePlaces()),
                () -> assertThat(group.getStudents()).isEqualTo(List.of(Student.builder().build()))
        );
    }

    @Test
    public void groupToGroupDtoShouldReturnEmptyGroup() {
        assertThat(groupDtoToGroup(null)).isEqualTo(Group.builder().build());
    }

    @Test
    public void groupDtoToGroupShouldHaveStudentDtosNullWhenNoStudents() {
        Group group = Group.builder()
                .withStudents(null)
                .build();
        assertThat(groupToGroupDto(group).getStudents()).isNull();
    }

    @Test
    public void groupToGroupDtoShouldHaveStudentsNullWhenNoStudentDtos() {
        GroupDto groupDto = GroupDto.builder()
                .withStudents(null)
                .build();
        assertThat(groupDtoToGroup(groupDto).getStudents()).isNull();
    }
}
