package org.foxminded.university.dao;

import org.foxminded.university.domain.Page;
import org.foxminded.university.entity.Group;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

@SpringJUnitConfig(SpringTestConfig.class)
@Sql(scripts = { "classpath:schemaTest.sql", "classpath:data.sql"})
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class GroupDaoTest {

    @Autowired
    private GroupDao groupDao;

    @Test
    void createShouldCreateAGroup() {
        int sizeBefore = groupDao.findAll().size();
        Group group =  Group.builder()
                .withId(5L)
                .withName( "lk-00")
                .build();
        groupDao.create(group);
        assertThat(groupDao.findAll())
                .hasSize(sizeBefore+1)
                .contains(group);
    }

    @Test
    void findByIdShouldFindGroup() {
        Group group =  Group.builder()
                .withId(1L)
                .withName("fd-41")
                .build();
        assertThat(groupDao.findById(1L)).hasValue(group);
    }

    @Test
    void updateShouldUpdateGroup() {
        Group group =  Group.builder()
                .withId(1L)
                .withName("fd-10")
                .build();
        groupDao.update(group);
        assertThat(groupDao.findById(1L)).hasValue(group);
    }

    @Test
    void deleteShouldDeleteGroup() {
        int sizeBefore = groupDao.findAll().size();
        groupDao.delete(1L);
        int sizeAfter = groupDao.findAll().size();
        assertThat(sizeAfter)
                .isEqualTo(sizeBefore-1);
    }

    @Test
    void findAllShouldFindAllLessons() {
        Group group =  Group.builder()
                .withId(1L)
                .withName("fd-41")
                .build();
        assertThat(groupDao.findAll())
                .isNotEmpty()
                .contains(group);
    }

    @Test
    void findAllShouldFindAllGroupsPageable() {
        Page page = new Page(0,3);
        assertThat(groupDao.findAll(page).getItems())
                .hasSize(3);
    }
}
