package org.foxminded.university.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.List;

@Data
@Builder(setterPrefix = "with")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString
@AllArgsConstructor
public class Course {
    @EqualsAndHashCode.Include
    private final Long id;

    @EqualsAndHashCode.Include
    private final String location;
    private final List<Group> groups;
    private final Schedule schedule;
    private final Lesson lesson;
    private final Teacher teacher;
}
