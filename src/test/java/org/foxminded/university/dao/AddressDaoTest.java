package org.foxminded.university.dao;

import org.foxminded.university.domain.Page;
import org.foxminded.university.entity.Address;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

@SpringJUnitConfig(SpringTestConfig.class)
@Sql(scripts = {"classpath:schemaTest.sql", "classpath:data.sql"})
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class AddressDaoTest {

    @Autowired
    private AddressDao addressDao;

    @Test
    void createShouldCreateAnAddress() {
        int sizeBefore = addressDao.findAll().size();
        Address address = Address.builder()
                .withId(6L)
                .withStreet("Yunosti")
                .withCity("Khmelnytskii")
                .withFlatNumber(48)
                .build();
        addressDao.create(address);
        assertThat(addressDao.findAll())
                .hasSize(sizeBefore + 1)
                .contains(address);
    }

    @Test
    void findByIdShouldFindAddress() {
        Address address = Address.builder()
                .withId(1L)
                .withCity("Kyiv")
                .withStreet("Peremohy")
                .withFlatNumber(4)
                .build();
        assertThat(addressDao.findById(1L)).hasValue(address);
    }

    @Test
    void updateShouldUpdateAddress() {
        Address address = Address.builder()
                .withId(1L)
                .withCity("Lviv")
                .withStreet("Kyivska")
                .withFlatNumber(5)
                .build();
        addressDao.update(address);
        assertThat(addressDao.findById(1L)).hasValue(address);
    }

    @Test
    void deleteShouldDeleteAddress() {
        int sizeBefore = addressDao.findAll().size();
        addressDao.delete(1L);
        int sizeAfter = addressDao.findAll().size();
        assertThat(sizeAfter)
                .isEqualTo(sizeBefore - 1);
    }

    @Test
    void findAllShouldFindAllAddresses() {
        Address address = Address.builder()
                .withId(1L)
                .withCity("Kyiv")
                .withStreet("Peremohy")
                .withFlatNumber(4)
                .build();
        assertThat(addressDao.findAll())
                .isNotEmpty()
                .contains(address);
    }

    @Test
    void findAllShouldFindAllAddressesPageable() {
        Page page = new Page(0, 3);
        assertThat(addressDao.findAll(page).getItems())
                .hasSize(3);
    }
}
