package org.foxminded.university.service;

import org.foxminded.university.dao.GroupDao;
import org.foxminded.university.domain.Page;
import org.foxminded.university.domain.Pageable;
import org.foxminded.university.dto.GroupDto;
import org.foxminded.university.entity.Group;
import org.foxminded.university.exception.ServiceException;
import org.foxminded.university.mapper.GroupMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.foxminded.university.mapper.GroupMapper.groupToGroupDto;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.internal.util.JavaEightUtil.emptyOptional;

@ExtendWith(MockitoExtension.class)
class GroupServiceTest {

    @Mock
    private GroupDao dao;

    @InjectMocks
    private GroupService service;

    private final Group group = Group.builder().withId(1L).withName("gi-41").withAvailablePlaces(20).build();

    @Test
    void findAllShouldFindAllGroups() {
        when(dao.findAll()).thenReturn(getGroups());

        assertThat(service.findAll(), hasItem(groupToGroupDto(group)));
    }

    @Test
    void findAllPageableShouldFindAllPageable() {
        Page page = new Page(0, 2);
        Pageable<Group> groupPageable = new Pageable<>(getGroups(), 0, 2);
        when(dao.findAll(page)).thenReturn(groupPageable);

        Pageable<GroupDto> groupDtoPageable = new Pageable<>(getGroups().stream().map(GroupMapper::groupToGroupDto).collect(Collectors.toList()), 0, 2);
        assertThat(service.findAll(page), equalTo(groupDtoPageable));
    }

    @Test
    void findByIdShouldFindGroupById() {
        when(dao.findById(6L)).thenReturn(Optional.of(group));

        assertThat(service.findById(6L), is(not(emptyOptional())));
    }

    @Test
    void createGroupShouldUseCreateMethod() {
        service.createGroup(groupToGroupDto(group));

        verify(dao).create(group);
    }

    @Test
    void updateGroupShouldUseUpdateMethod() {
        service.updateGroup(groupToGroupDto(group));

        verify(dao).update(group);
    }

    @Test
    void deleteGroupByIdShouldUseDeleteMethod() {
        Long id = 5L;
        service.deleteGroupById(id);

        verify(dao).delete(id);
    }

    @Test
    void assignStudentToGroupShouldAssignStudentToGroup() {
        when(dao.findById(6L)).thenReturn(Optional.of(group));
        service.assignStudentToGroup(1L, 6L);

        verify(dao).assignStudentToGroup(1L, group);
        verify(dao).update(Group.builder()
                .withId(1L)
                .withName("gi-41")
                .withAvailablePlaces(20)
                .build());
    }

    @Test
    void assignStudentToGroupShouldNotAssignStudentToGroup() {
        when(dao.findById(6L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.assignStudentToGroup(1L, 6L))
                .isExactlyInstanceOf(ServiceException.class)
                .hasMessage("Unfortunately group with this id not found");
    }

    @Test
    void assignStudentToGroupShouldNotAssignStudentToGroupWithNoAvailablePlaces() {
        long Id = 6L;
        Group groupWithNoAvailablePlaces = Group.builder()
                .withId(Id)
                .withAvailablePlaces(0)
                .build();
        when(dao.findById(Id)).thenReturn(Optional.of(groupWithNoAvailablePlaces));

        assertThatThrownBy(() -> service.assignStudentToGroup(1L, Id))
                .isExactlyInstanceOf(ServiceException.class)
                .hasMessage("Unfortunately there is no available places in the group");
    }

    private List<Group> getGroups() {
        List<Group> groups = new ArrayList<>();
        groups.add(Group.builder().withId(1L).withName("gi-41").build());
        groups.add(Group.builder().withId(2L).withName("us-48").build());
        return groups;
    }
}
