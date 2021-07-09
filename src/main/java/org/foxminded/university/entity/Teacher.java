package org.foxminded.university.entity;

import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Getter
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
