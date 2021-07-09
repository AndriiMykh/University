package org.foxminded.university.entity;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.sql.Time;
import java.time.LocalDate;

@Getter
@Builder(setterPrefix = "with")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString
public class Schedule {
    @EqualsAndHashCode.Include
    private final Long id;
    private final LocalDate date;
    private final Time startTime;
    private final Time endTime;
}
