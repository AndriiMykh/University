package org.foxminded.university.converter;

import org.springframework.core.convert.converter.Converter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class String2LocalDateConverter implements Converter<String, LocalDate> {
    @Override
    public LocalDate convert(String s) {
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return LocalDate.parse(s, fmt);
    }
}
