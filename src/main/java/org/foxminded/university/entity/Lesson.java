package org.foxminded.university.entity;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Builder(setterPrefix = "with")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString
public class Lesson {
    @EqualsAndHashCode.Include
    private final Long id;

    @EqualsAndHashCode.Include
    private final String name;
    private final String description;
}
