package org.foxminded.university.dao;

import org.foxminded.university.entity.Student;

import java.util.Optional;

public interface StudentDao extends AbstractDao<Long, Student> {

    Optional<Student> findByEmail(String email);
}
