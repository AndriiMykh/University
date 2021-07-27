package org.foxminded.university.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder(setterPrefix = "with")
public class Teacher extends Person {
    private final String linkedinUrl;

    @Override
    public String toString() {
        return "Teacher" +
                super.toString() +
                "linkedinUrl='" + linkedinUrl + '\'' +
                '}';
    }
}
