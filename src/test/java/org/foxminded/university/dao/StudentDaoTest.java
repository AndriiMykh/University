package org.foxminded.university.dao;

import org.assertj.core.api.AssertionsForClassTypes;
import org.foxminded.university.domain.Page;
import org.foxminded.university.entity.Address;
import org.foxminded.university.entity.Group;
import org.foxminded.university.entity.Student;;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.sql.Date;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.foxminded.university.entity.StudiesType.FULL_TIME;

@SpringJUnitConfig(SpringTestConfig.class)
@Sql(scripts = {"classpath:schemaTest.sql", "classpath:data.sql"})
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class StudentDaoTest {

    @Autowired
    private StudentDao studentDao;

    @Test
    void createShouldCreateAStudent() {
        int sizeBefore = studentDao.findAll().size();
        Student student = Student.builder()
                .withId(3L)
                .withFirstName("Kyrylo")
                .withLastName("Dymin")
                .withBirthDate(new Date(1000))
                .withAddress(Address.builder().withId(3L).build())
                .withPhoneNumber("123132512")
                .withEmail("Kyrylo@gmail.com")
                .withPassword("1111")
                .withGroup(Group.builder().withId(1L).build())
                .withStudiesType(FULL_TIME)
                .build();
        studentDao.create(student);

        assertThat(studentDao.findAll())
                .hasSize(sizeBefore + 1)
                .contains(student);
    }

    @Test
    void findByIdShouldFindByIdStudent() {
        Student student = Student.builder()
                .withId(1L)
                .withFirstName("Dmytro")
                .withLastName("Serheev")
                .build();
        assertThat(studentDao.findById(1L))
                .hasValue(student);
    }

    @Test
    void updateShouldUpdateStudent() {
        Student student = Student.builder()
                .withId(1L)
                .withFirstName("Daniil")
                .withLastName("Danilov")
                .withBirthDate(new Date(1000))
                .withAddress(Address.builder().withId(3L).build())
                .withPhoneNumber("123132512")
                .withEmail("Mykhailo@gmail.com")
                .withPassword("1111")
                .withGroup(Group.builder().withId(1L).build())
                .withStudiesType(FULL_TIME)
                .build();
        studentDao.update(student);
        AssertionsForClassTypes.assertThat(studentDao.findById(1L))
                .hasValue(student);
    }

    @Test
    void deleteShouldDeleteStudent() {
        int sizeBefore = studentDao.findAll().size();
        studentDao.delete(1L);
        int sizeAfter = studentDao.findAll().size();
        assertThat(sizeAfter)
                .isEqualTo(sizeBefore - 1);
    }

    @Test
    void findAllShouldFindAllStudent() {
        Student student = Student.builder()
                .withId(1L)
                .withFirstName("Dmytro")
                .withLastName("Serheev")
                .build();
        assertThat(studentDao.findAll())
                .isNotEmpty()
                .contains(student);
    }

    @Test
    void findAllShouldFindAllStudentsPageable() {
        Page page = new Page(0, 2);
        assertThat(studentDao.findAll(page).getItems())
                .hasSize(2);
    }

    @Test
    void findByEmailShouldFindByEmail() {
        assertThat(studentDao.findByEmail("Dmytro@gmail.com"))
                .isNotEmpty();
    }
}
