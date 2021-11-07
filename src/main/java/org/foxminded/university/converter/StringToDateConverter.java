package org.foxminded.university.converter;

import lombok.SneakyThrows;
import org.springframework.core.convert.converter.Converter;

import java.text.SimpleDateFormat;
import java.util.Date;

public class StringToDateConverter implements Converter<String, Date> {
    @SneakyThrows
    @Override
    public Date convert(String date) {
        if (date == null) {
        return new SimpleDateFormat("yyyy-MM-dd").parse(date);
        }else {
            return null;
        }

    }
}
