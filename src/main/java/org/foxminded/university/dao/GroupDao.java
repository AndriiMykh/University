package org.foxminded.university.dao;

import org.foxminded.university.entity.Group;

public interface GroupDao extends AbstractDao<Long, Group> {

    void assignStudentToGroup(Long studentId, Group group);
}
