package org.foxminded.university.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.foxminded.university.dao.GroupDao;
import org.foxminded.university.domain.Page;
import org.foxminded.university.domain.Pageable;
import org.foxminded.university.entity.Group;
import org.foxminded.university.exception.ServiceException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
@Slf4j
public class GroupService {
    private final GroupDao groupDao;

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

    public void assignStudentToGroup(Long studentId, Long groupId) {
        Optional<Group> foundGroup = findById(groupId);
        if (foundGroup.isPresent()) {
            Group group = foundGroup.get();
            if (group.getAvailablePlaces() > 0) {
                Group updatedGroup = Group.builder()
                        .withId(group.getId())
                        .withName(group.getName())
                        .withAvailablePlaces(group.getAvailablePlaces() - 1)
                        .build();
                groupDao.assignStudentToGroup(studentId, group);
                groupDao.update(updatedGroup);
            } else {
                throw new ServiceException("Unfortunately there is no available places in the group");
            }
        } else {
            throw new ServiceException("Unfortunately group with this id not found");
        }
    }
}
