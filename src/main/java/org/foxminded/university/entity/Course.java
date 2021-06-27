package org.foxminded.university.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Course {
    private final Long id;
    private final String location;
    private final List<Group> groups;
    private final Schedule schedule;
    private final Lesson lesson;
    private final Teacher teacher;

    private Course(Builder builder) {
        this.id = builder.id;
        this.location = builder.location;
        this.lesson = builder.lesson;
        this.teacher = builder.teacher;
        this.schedule = builder.schedule;
        this.groups = new ArrayList<>();
    }

    public void addGroup(Group group) {
        this.groups.add(group);
    }

    public void removeGroup(Group group) {
        this.groups.remove(group);
    }

    public Long getId() {
        return id;
    }

    public String getLocation() {
        return location;
    }

    public List<Group> getGroups() {
        return groups;
    }

    public Schedule getSchedule() {
        return schedule;
    }

    public Lesson getLesson() {
        return lesson;
    }

    public Teacher getTeacher() {
        return teacher;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Course course = (Course) o;
        return Objects.equals(id, course.id) &&
                Objects.equals(location, course.location);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, location);
    }

    public static class Builder {

        private Long id;
        private String location;
        private Lesson lesson;
        private Teacher teacher;
        private Schedule schedule;

        private Builder() {
        }

        public Builder withId(Long id) {
            this.id = id;
            return Builder.this;
        }

        public Builder withLocation(String location) {
            this.location = location;
            return Builder.this;
        }

        public Builder withLesson(Lesson lesson) {
            this.lesson = lesson;
            return Builder.this;
        }

        public Builder withTeacher(Teacher teacher) {
            this.teacher = teacher;
            return Builder.this;
        }

        public Builder withSchedule(Schedule schedule) {
            this.schedule = schedule;
            return Builder.this;
        }

        public Course build() {
            return new Course(this);
        }
    }

    public static Builder builder() {
        return new Builder();
    }

    @Override
    public String toString() {
        return "Course{" +
                "id=" + id +
                ", location='" + location + '\'' +
                ", groups=" + groups +
                ", schedule=" + schedule +
                ", lesson=" + lesson +
                ", teacher=" + teacher +
                '}';
    }
}
