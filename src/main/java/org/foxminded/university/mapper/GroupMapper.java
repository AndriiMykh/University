package org.foxminded.university.mapper;

import org.foxminded.university.dto.GroupDto;
import org.foxminded.university.dto.StudentDto;
import org.foxminded.university.entity.Group;
import org.foxminded.university.entity.Student;

import java.util.List;
import java.util.stream.Collectors;

public class GroupMapper {
    public static GroupDto groupToGroupDto(Group group) {
        if (group != null) {
            GroupDto groupDto = GroupDto.builder()
                    .withId(group.getId())
                    .withName(group.getName())
                    .withAvailablePlaces(group.getAvailablePlaces())
                    .withStudents(getStudentsDto(group))
                    .build();
            return groupDto;
        } else {
            return GroupDto.builder().build();
        }
    }

    public static Group groupDtoToGroup(GroupDto groupDto) {
        if (groupDto != null) {
            Group group = Group.builder()
                    .withId(groupDto.getId())
                    .withName(groupDto.getName())
                    .withAvailablePlaces(groupDto.getAvailablePlaces())
                    .withStudents(getStudents(groupDto))
                    .build();
            return group;
        } else {
            return Group.builder().build();
        }
    }

    private static List<Student> getStudents(GroupDto groupDto) {
        return groupDto.getStudents() !=null
                ? groupDto.getStudents().stream().map(StudentMapper::studentDtoToStudent).collect(Collectors.toList())
                : null;
    }

    private static List<StudentDto> getStudentsDto(Group group) {
        return group.getStudents() !=null
                ? group.getStudents().stream().map(StudentMapper::studentToStudentDto).collect(Collectors.toList())
                : null;
    }
}
