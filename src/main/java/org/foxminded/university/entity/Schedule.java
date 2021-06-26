package org.foxminded.university.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Schedule {
    private final Course course;
    private final List<Date> dates;
    private final LocalDateTime startTime;
    private final LocalDateTime endTime;

    public Schedule(Course course, LocalDateTime startTime, LocalDateTime endTime) {
        this.course = course;
        this.startTime = startTime;
        this.endTime = endTime;
        this.dates = new ArrayList<>();
    }

    @Override
    public String toString() {
        return "Schedule{" +
                "course=" + course +
                ", dates=" + dates +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                '}';
    }
}
