package org.foxminded.university.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@Builder(setterPrefix = "with")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString
@AllArgsConstructor
public class Address {
    @EqualsAndHashCode.Include
    private final Long id;

    @EqualsAndHashCode.Include
    private final String city;

    @EqualsAndHashCode.Include
    private final String street;

    @EqualsAndHashCode.Include
    private final int flatNumber;
}
