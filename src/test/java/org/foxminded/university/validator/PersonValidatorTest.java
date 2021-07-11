package org.foxminded.university.validator;

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
        Teacher teacher = Teacher.builder()
                .withEmail("Mykhailofdfd")
                .build();
        assertThatThrownBy(() -> validator.personValidator(teacher))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Wrong format of email");
    }

    @Test
    void personValidatorShouldThrowIllegalArgumentExceptionWhenEmailIsEmpty() {
        Teacher teacher = Teacher.builder()
                .withEmail("")
                .build();
        assertThatThrownBy(() -> validator.personValidator(teacher))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Cannot be empty");
    }

    @Test
    void personValidatorShouldThrowIllegalArgumentExceptionWhenEmailIsNull() {
        Teacher teacher = Teacher.builder()
                .build();
        assertThatThrownBy(() -> validator.personValidator(teacher))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Cannot be empty");
    }

    @Test
    void personValidatorShouldThrowIllegalArgumentExceptionWhenPasswordDoesntContain4Chars() {
        Teacher teacher = Teacher.builder()
                .withEmail("aaa@gmail.com")
                .withPassword("123")
                .build();
        assertThatThrownBy(() -> validator.personValidator(teacher))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Please provide at least 4 characters");
    }

    @Test
    void personValidatorShouldThrowIllegalArgumentExceptionWhenPasswordIsNull() {
        Teacher teacher = Teacher.builder()
                .withEmail("aaa@gmail.com")
                .build();
        assertThatThrownBy(() -> validator.personValidator(teacher))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Cannot be empty");
    }

    @Test
    void personValidatorShouldThrowIllegalArgumentExceptionWhenPasswordIsEmpty() {
        Teacher teacher = Teacher.builder()
                .withEmail("aaa@gmail.com")
                .withPassword("")
                .build();
        assertThatThrownBy(() -> validator.personValidator(teacher))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Cannot be empty");
    }

    @Test
    void personValidatorShouldThrowIllegalArgumentExceptionWhenBirthDateIsNull() {
        Teacher teacher = Teacher.builder()
                .withEmail("aaa@gmail.com")
                .withPassword("1234")
                .build();
        assertThatThrownBy(() -> validator.personValidator(teacher))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Cannot be empty");
    }

    @Test
    void personValidatorShouldThrowIllegalArgumentExceptionWhenBirthDateIsInFuture() {
        Teacher teacher = Teacher.builder()
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
        Teacher teacher = Teacher.builder()
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
