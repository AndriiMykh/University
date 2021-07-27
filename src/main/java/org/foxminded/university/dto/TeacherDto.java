package org.foxminded.university.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder(setterPrefix = "with")
@RequiredArgsConstructor
public class TeacherDto extends PersonDto {
    private String linkedinUrl;
}
