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
public class Group {
    @EqualsAndHashCode.Include
    private final Long id;

    @EqualsAndHashCode.Include
    private final String name;
    private final int availablePlaces;
    private final List<Student> students;
}
