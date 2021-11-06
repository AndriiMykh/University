package org.foxminded.university.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.foxminded.university.dao.GroupDao;
import org.foxminded.university.domain.Page;
import org.foxminded.university.domain.Pageable;
import org.foxminded.university.dto.GroupDto;
import org.foxminded.university.entity.Group;
import org.foxminded.university.exception.ServiceException;
import org.foxminded.university.mapper.GroupMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.foxminded.university.mapper.GroupMapper.groupDtoToGroup;
import static org.foxminded.university.mapper.GroupMapper.groupToGroupDto;

@Service
@AllArgsConstructor
@Slf4j
public class GroupService {
    private final GroupDao groupDao;

    public List<GroupDto> findAll() {
        return groupDao.findAll()
                .stream()
                .map(GroupMapper::groupToGroupDto)
                .collect(Collectors.toList());
    }

    public Pageable<GroupDto> findAll(Page page) {
        List<GroupDto> groupDtos = groupDao.findAll(page).getItems()
                .stream()
                .map(GroupMapper::groupToGroupDto)
                .collect(Collectors.toList());
        return new Pageable<>(groupDtos, page.getPageNumber(), page.getItemsPerPage());
    }

    public GroupDto findById(Long id) {
        Optional<Group> group = groupDao.findById(id);
        if(group.isEmpty()){
            throw new ServiceException("Group not found with id: "+ id);
        }
        return groupToGroupDto(group.get());
    }

    public void createGroup(GroupDto group) {
        groupDao.create(groupDtoToGroup(group));
    }

    public void updateGroup(GroupDto group) {
        groupDao.update(groupDtoToGroup(group));
    }

    public void deleteGroupById(Long id) {
        groupDao.delete(id);
    }

    public void assignStudentToGroup(Long studentId, Long groupId) {
        Optional<Group> foundGroup = groupDao.findById(groupId);
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
