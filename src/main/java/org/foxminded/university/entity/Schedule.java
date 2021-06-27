package org.foxminded.university.entity;

import java.sql.Time;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Objects;

public class Schedule {
    private final Long id;
    private final Date date;
    private final Time startTime;
    private final Time endTime;

    public Schedule(Builder builder) {
        this.id = builder.id;
        this.startTime = builder.startTime;
        this.endTime = builder.endTime;
        this.date = builder.date;
    }

    public Long getId() {
        return id;
    }

    public Date getDate() {
        return date;
    }

    public Time getStartTime() {
        return startTime;
    }

    public Time getEndTime() {
        return endTime;
    }

    public static class Builder {
        private  Long id;
        private  Course course;
        private  Date date;
        private  Time startTime;
        private  Time endTime;

        private Builder() {
        }

        public Builder withId(Long id) {
            this.id = id;
            return this;
        }

        public Builder withDate(Date date) {
            this.date = date;
            return this;
        }

        public Builder withStartTime(Time flatNumber) {
            this.startTime = startTime;
            return this;
        }
        public Builder withEndTime(Time flatNumber) {
            this.startTime = startTime;
            return this;
        }

        public Schedule build() {
            return new Schedule(this);
        }
    }

    public static Builder builder() {
        return new Builder();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Schedule schedule = (Schedule) o;
        return Objects.equals(id, schedule.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Schedule{" +
                "id=" + id +
                ", date=" + date +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                '}';
    }
}
