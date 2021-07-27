package org.foxminded.university.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.foxminded.university.entity.StudiesType;

@Getter
@Setter
@SuperBuilder(setterPrefix = "with")
@RequiredArgsConstructor
public class StudentDto extends PersonDto {
    private GroupDto group;
    private StudiesType studiesType;

    @Override
    public String toString() {
        return "StudentDto " +
                super.toString() +
                ", group=" + group +
                ", studiesType='" + studiesType + '\'' +
                '}';
    }
}
