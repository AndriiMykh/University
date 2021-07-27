package org.foxminded.university.validator;

import org.foxminded.university.dto.TeacherDto;
import org.foxminded.university.entity.Teacher;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class PersonValidatorTest {
    private final PersonValidator validator = new PersonValidator();

    @Test
    void personValidatorShouldThrowIllegalArgumentExceptionWhenEmailIsIncorrect() {
        TeacherDto teacher = TeacherDto.builder()
                .withEmail("Mykhailofdfd")
                .build();
        assertThatThrownBy(() -> validator.personValidator(teacher))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Wrong format of email");
    }

    @Test
    void personValidatorShouldThrowIllegalArgumentExceptionWhenEmailIsEmpty() {
        TeacherDto teacher = TeacherDto.builder()
                .withEmail("")
                .build();
        assertThatThrownBy(() -> validator.personValidator(teacher))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Cannot be empty");
    }

    @Test
    void personValidatorShouldThrowIllegalArgumentExceptionWhenEmailIsNull() {
        TeacherDto teacher = TeacherDto.builder()
                .build();
        assertThatThrownBy(() -> validator.personValidator(teacher))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Cannot be empty");
    }

    @Test
    void personValidatorShouldThrowIllegalArgumentExceptionWhenPasswordDoesntContain4Chars() {
        TeacherDto teacher = TeacherDto.builder()
                .withEmail("aaa@gmail.com")
                .withPassword("123")
                .build();
        assertThatThrownBy(() -> validator.personValidator(teacher))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Please provide at least 4 characters");
    }

    @Test
    void personValidatorShouldThrowIllegalArgumentExceptionWhenPasswordIsNull() {
        TeacherDto teacher = TeacherDto.builder()
                .withEmail("aaa@gmail.com")
                .build();
        assertThatThrownBy(() -> validator.personValidator(teacher))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Cannot be empty");
    }

    @Test
    void personValidatorShouldThrowIllegalArgumentExceptionWhenPasswordIsEmpty() {
        TeacherDto teacher = TeacherDto.builder()
                .withEmail("aaa@gmail.com")
                .withPassword("")
                .build();
        assertThatThrownBy(() -> validator.personValidator(teacher))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Cannot be empty");
    }

    @Test
    void personValidatorShouldThrowIllegalArgumentExceptionWhenBirthDateIsNull() {
        TeacherDto teacher = TeacherDto.builder()
                .withEmail("aaa@gmail.com")
                .withPassword("1234")
                .build();
        assertThatThrownBy(() -> validator.personValidator(teacher))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Cannot be empty");
    }

    @Test
    void personValidatorShouldThrowIllegalArgumentExceptionWhenBirthDateIsInFuture() {
        TeacherDto teacher = TeacherDto.builder()
                .withEmail("aaa@gmail.com")
                .withPassword("1234")
                .withBirthDate(convertToDateViaInstant(LocalDate.now().plusWeeks(1)))
                .build();
        assertThatThrownBy(() -> validator.personValidator(teacher))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Please provide correct date");
    }

    @Test
    void personValidatorShouldNotThrowAnyExceptionWhenValuesAreCorrect() {
        TeacherDto teacher = TeacherDto.builder()
                .withEmail("aaa@gmail.com")
                .withPassword("1234")
                .withBirthDate(convertToDateViaInstant(LocalDate.now().minusDays(1)))
                .build();
        assertDoesNotThrow(() -> validator.personValidator(teacher));
    }

    private Date convertToDateViaInstant(LocalDate dateToConvert) {
        return java.util.Date.from(dateToConvert.atStartOfDay()
                .atZone(ZoneId.systemDefault())
                .toInstant());
    }
}
