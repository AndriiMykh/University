package org.foxminded.university.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;

public class Group {
    private final Long id;
    private final String name;
    private final List<Student> students;

    public Group(String name) {
        this.id = null;
        this.name = name;
        this.students = new ArrayList<>();
    }

    public Group(long id) {
        this.id = id;
        this.name = null;
        this.students = new ArrayList<>();
    }

    public Group(Long id, String name) {
        this.id = id;
        this.name = name;
        this.students = new ArrayList<>();
    }



    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<Student> getStudents() {
        return students;
    }

    void addStudent(Student student) {
        this.students.add(student);
    }

    void removeStudentFromGroup(Long id) {
        Predicate<Student> studentById = student -> student.getId() == id;
        this.students.removeIf(studentById);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Group group = (Group) o;
        return Objects.equals(id, group.id) &&
                Objects.equals(name, group.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }

    @Override
    public String toString() {
        return "Group{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", students=" + students +
                '}';
    }

}
