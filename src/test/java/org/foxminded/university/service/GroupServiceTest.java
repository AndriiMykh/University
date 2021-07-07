package org.foxminded.university.service;

import org.foxminded.university.dao.GroupDao;
import org.foxminded.university.domain.Page;
import org.foxminded.university.domain.Pageable;
import org.foxminded.university.entity.Group;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GroupServiceTest {

    @Mock
    private GroupDao dao;
    @InjectMocks
    private GroupService service;

    private final Group group = new Group(1L, "gi-41");
    @Test
    void findAllShouldFindAllGroups() {
        when(dao.findAll()).thenReturn(getGroups());

        assertThat(service.findAll())
                .isNotEmpty()
                .contains(group);
    }

    @Test
    void findAllPageableShouldFindAllPageable() {
        Page page = new Page(0, 2);
        Pageable<Group> groupPageable = new Pageable<>(getGroups(), 0,2);
        when(dao.findAll(page)).thenReturn(groupPageable);

        assertThat(service.findAll(page))
                .isEqualTo(groupPageable);
    }

    @Test
    void findByIdShouldFindGroupById() {
        when(dao.findById(6L)).thenReturn(Optional.of(group));

        assertThat(service.findById(6L))
                .hasValue(group);
    }

    @Test
    void createGroupShouldUseCreateMethod() {
        service.createGroup(group);

        verify(dao).create(group);
    }

    @Test
    void updateGroupShouldUseUpdateMethod() {
        service.updateGroup(group);

        verify(dao).update(group);
    }

    @Test
    void deleteGroupByIdShouldUseDeleteMethod() {
        Long id = 5L;
        service.deleteGroupById(id);

        verify(dao).delete(id);
    }

    private List<Group> getGroups(){
        List<Group> groups = new ArrayList<>();
        groups.add(new Group(1L, "gi-41"));
        groups.add(new Group(2L, "us-48"));
        return groups;
    }
}
