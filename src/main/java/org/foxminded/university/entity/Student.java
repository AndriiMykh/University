package org.foxminded.university.entity;

import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder(setterPrefix = "with")
public class Student extends Person {
    private final Group group;
    private final StudiesType studiesType;

    @Override
    public String toString() {
        return "Student" +
                super.toString() +
                ", group=" + group +
                ", studiesType='" + studiesType + '\'' +
                '}';
    }
}
