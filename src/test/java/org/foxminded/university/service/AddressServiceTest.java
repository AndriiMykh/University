package org.foxminded.university.service;

import org.foxminded.university.dao.AddressDao;
import org.foxminded.university.domain.Page;
import org.foxminded.university.domain.Pageable;
import org.foxminded.university.entity.Address;
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
class AddressServiceTest {

    @Mock
    private AddressDao dao;
    @InjectMocks
    private AddressService service;

    private final Address address = Address.builder()
            .withId(6L)
                .withStreet("Yunosti")
                .withCity("Khmelnytskii")
                .withFlatNumber(48)
                .build();
    @Test
    void findAllShouldFindAllAddresses() {
        when(dao.findAll()).thenReturn(getAddresses());

        assertThat(service.findAll())
                .isNotEmpty()
                .contains(address);
    }

    @Test
    void findAllPagableShouldFindAllStudentsPagable() {
        Page page = new Page(0, 2);
        Pageable<Address> addressPageable = new Pageable<>(getAddresses(), 0,2);
        when(dao.findAll(page)).thenReturn(addressPageable);

        assertThat(service.findAll(page))
                .isEqualTo(addressPageable);
    }

    @Test
    void findByIdShouldFindByIdAddress() {
        when(dao.findById(6L)).thenReturn(Optional.of(address));

        assertThat(service.findById(6L))
                .hasValue(address);
    }

    @Test
    void createAddressShouldUseCreateMethod() {
        service.createAddress(address);

        verify(dao).create(address);
    }

    @Test
    void updateAddressShouldUseUpdateMethod() {
        service.updateAddress(address);

        verify(dao).update(address);
    }

    @Test
    void deleteAddressByIdShouldUseDeleteById() {
        Long id = 5L;
        service.deleteAddressById(id);

        verify(dao).delete(id);
    }

    private List<Address> getAddresses(){
        List<Address> addresses = new ArrayList<>();
        addresses.add(Address.builder()
                .withId(6L)
                .withStreet("Yunosti")
                .withCity("Khmelnytskii")
                .withFlatNumber(48)
                .build());
        addresses.add(Address.builder()
                .withId(1L)
                .withStreet("Buforowa")
                .withCity("Warshawa")
                .withFlatNumber(100)
                .build());
        return addresses;
    }
}
