package org.foxminded.university.dao;

import org.foxminded.university.entity.Teacher;

import java.util.Optional;

public interface TeacherDao extends AbstractDao<Long, Teacher> {

    Optional<Teacher> findByEmail(String email);
}
