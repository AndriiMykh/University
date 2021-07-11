package org.foxminded.university.dao;

import org.assertj.core.api.AssertionsForClassTypes;
import org.foxminded.university.domain.Page;
import org.foxminded.university.entity.Address;
import org.foxminded.university.entity.Teacher;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.sql.Date;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

@SpringJUnitConfig(SpringTestConfig.class)
@Sql(scripts = {"classpath:schemaTest.sql", "classpath:data.sql"})
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class TeacherDaoTest {

    @Autowired
    private TeacherDao teacherDao;

    @Test
    void createShouldCreateATeacher() {
        int sizeBefore = teacherDao.findAll().size();
        Teacher teacher = Teacher.builder()
                .withId(3L)
                .withFirstName("Mykhailo")
                .withLastName("Dymin")
                .withBirthDate(new Date(1000))
                .withAddress(Address.builder().withId(3L).build())
                .withPhoneNumber("123132512")
                .withEmail("Mykhailo@gmail.com")
                .withPassword("1111")
                .withLinkedinUrl("linkedin.com/in/Mykhailo/")
                .build();
        teacherDao.create(teacher);

        assertThat(teacherDao.findAll())
                .hasSize(sizeBefore + 1)
                .contains(teacher);
    }

    @Test
    void findByIdShouldFindByIdTeacher() {
        Teacher teacher = Teacher.builder()
                .withId(1L)
                .withFirstName("Mykola")
                .withLastName("Serheev")
                .build();
        assertThat(teacherDao.findById(1L))
                .hasValue(teacher);
    }

    @Test
    void updateShouldUpdateTeacher() {
        Teacher teacher = Teacher.builder()
                .withId(1L)
                .withFirstName("Daniil")
                .withLastName("Danilov")
                .withBirthDate(new Date(1000))
                .withAddress(Address.builder().withId(3L).build())
                .withPhoneNumber("123132512")
                .withEmail("Mykhailo@gmail.com")
                .withPassword("1111")
                .withLinkedinUrl("linkedin.com/in/Daniil/")
                .build();
        teacherDao.update(teacher);
        assertThat(teacherDao.findById(1L))
                .hasValue(teacher);
    }

    @Test
    void deleteShouldDeleteTeacher() {
        int sizeBefore = teacherDao.findAll().size();
        teacherDao.delete(1L);
        int sizeAfter = teacherDao.findAll().size();
        assertThat(sizeAfter)
                .isEqualTo(sizeBefore - 1);
    }

    @Test
    void findAllShouldFindAllLessons() {
        Teacher teacher = Teacher.builder()
                .withId(1L)
                .withFirstName("Mykola")
                .withLastName("Serheev")
                .build();
        assertThat(teacherDao.findAll())
                .isNotEmpty()
                .contains(teacher);
    }

    @Test
    void findAllShouldFindAllTeachersPageable() {
        Page page = new Page(0, 2);
        assertThat(teacherDao.findAll(page).getItems())
                .hasSize(2);
    }

    @Test
    void findByEmailShouldFindByEmail() {
        assertThat(teacherDao.findByEmail("vlad@gmail.com"))
                .isNotEmpty();
    }
}
