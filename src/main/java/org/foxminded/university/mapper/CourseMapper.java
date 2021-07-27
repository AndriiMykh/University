package org.foxminded.university.mapper;

import org.foxminded.university.dto.CourseDto;
import org.foxminded.university.dto.GroupDto;
import org.foxminded.university.entity.Course;
import org.foxminded.university.entity.Group;

import java.util.List;
import java.util.stream.Collectors;

import static org.foxminded.university.mapper.LessonMapper.lessonDtoToLesson;
import static org.foxminded.university.mapper.LessonMapper.lessonToLessonDto;
import static org.foxminded.university.mapper.ScheduleMapper.scheduleDtoToSchedule;
import static org.foxminded.university.mapper.ScheduleMapper.scheduleToScheduleDto;
import static org.foxminded.university.mapper.TeacherMapper.teacherDtoToTeacher;
import static org.foxminded.university.mapper.TeacherMapper.teacherToTeacherDto;

public class CourseMapper {
    public static CourseDto courseToCourseDto(Course course){
        if (course != null) {
            CourseDto courseDto = CourseDto.builder()
                    .withId(course.getId())
                    .withTeacher(teacherToTeacherDto(course.getTeacher()))
                    .withLesson(lessonToLessonDto(course.getLesson()))
                    .withSchedule(scheduleToScheduleDto(course.getSchedule()))
                    .withLocation(course.getLocation())
                    .withGroups(getGroups(course))
                    .build();
            return courseDto;
        }else {
            return CourseDto.builder().build();
        }
    }

    public static Course courseDtoToCourse(CourseDto courseDto){
        if (courseDto != null) {
            Course course = Course.builder()
                    .withId(courseDto.getId())
                    .withTeacher(teacherDtoToTeacher(courseDto.getTeacher()))
                    .withLesson(lessonDtoToLesson(courseDto.getLesson()))
                    .withSchedule(scheduleDtoToSchedule(courseDto.getSchedule()))
                    .withLocation(courseDto.getLocation())
                    .withGroups(getGroupsDto(courseDto))
                    .build();
            return course;
        }else {
            return Course.builder().build();
        }
    }

    private static List<Group> getGroupsDto(CourseDto courseDto) {
        return courseDto.getGroups() !=null ?
                courseDto.getGroups().stream().map(GroupMapper::groupDtoToGroup).collect(Collectors.toList())
                : null;
    }

    private static List<GroupDto> getGroups(Course course) {
        return course.getGroups() !=null
                ? course.getGroups().stream().map(GroupMapper::groupToGroupDto).collect(Collectors.toList())
                : null;
    }
}
