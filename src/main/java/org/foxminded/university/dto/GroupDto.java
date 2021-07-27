package org.foxminded.university.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder(setterPrefix = "with")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class GroupDto {
    @EqualsAndHashCode.Include
    private Long id;

    @EqualsAndHashCode.Include
    private String name;
    private int availablePlaces;
    private List<StudentDto> students;
}
