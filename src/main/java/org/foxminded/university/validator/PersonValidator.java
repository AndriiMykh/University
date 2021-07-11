package org.foxminded.university.validator;

import org.foxminded.university.entity.Person;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class PersonValidator {
    private static final String EMAIL_PATTERN = "^(.+)@(.+)$";
    private static final String PASSWORD_PATTERN = "^[^\\s]{4,}$";
    private static final String EMPTY_MESSAGE = "Cannot be empty";

    public void personValidator(Person person) {
        emailValidator(person);
        passwordValidator(person);
        birthDateValidator(person);
    }

    private void emailValidator(Person person) {
        validateIfNotEmpty(person.getEmail());
        if (!person.getEmail().matches(EMAIL_PATTERN)) {
            throw new IllegalArgumentException("Wrong format of email");
        }
    }

    private void passwordValidator(Person person) {
        validateIfNotEmpty(person.getPassword());
        if (!person.getPassword().matches(PASSWORD_PATTERN)) {
            throw new IllegalArgumentException("Please provide at least 4 characters");
        }
    }

    private void validateIfNotEmpty(String string) {
        if (string == null) {
            throw new IllegalArgumentException(EMPTY_MESSAGE);
        }
        if (string.isEmpty()) {
            throw new IllegalArgumentException(EMPTY_MESSAGE);
        }
    }

    private void birthDateValidator(Person person) {
        if (person.getBirthDate() == null) {
            throw new IllegalArgumentException(EMPTY_MESSAGE);
        }
        if (!person.getBirthDate().before(new Date())) {
            throw new IllegalArgumentException("Please provide correct date");
        }
    }
}
