package org.foxminded.university.converter;

import org.springframework.core.convert.converter.Converter;

import java.sql.Time;

public class String2TimeConverter implements Converter<String, Time> {
    @Override
    public Time convert(String s) {
        String[] time =  s.split(":");
        return new Time(Integer.parseInt(time[0]),Integer.parseInt(time[1]),0);
    }
}
