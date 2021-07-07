package org.foxminded.university.service;

import org.foxminded.university.dao.GroupDao;
import org.foxminded.university.domain.Page;
import org.foxminded.university.domain.Pageable;
import org.foxminded.university.entity.Group;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GroupService {
    private GroupDao groupDao;

    @Autowired
    public GroupService(GroupDao groupDao) {
        this.groupDao = groupDao;
    }

    public List<Group> findAll() {
        return groupDao.findAll();
    }

    public Pageable<Group> findAll(Page page) {
        return groupDao.findAll(page);
    }

    public Optional<Group> findById(Long id) {
        return groupDao.findById(id);
    }

    public void createGroup(Group group) {
        groupDao.create(group);
    }

    public void updateGroup(Group group) {
        groupDao.update(group);
    }

    public void deleteGroupById(Long id) {
        groupDao.delete(id);
    }
}
