package org.foxminded.university.service;

import lombok.AllArgsConstructor;
import org.foxminded.university.dao.AddressDao;
import org.foxminded.university.domain.Page;
import org.foxminded.university.domain.Pageable;
import org.foxminded.university.entity.Address;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class AddressService {
    private final AddressDao addressDao;

    public List<Address> findAll() {
        return addressDao.findAll();
    }

    public Pageable<Address> findAll(Page page) {
        return addressDao.findAll(page);
    }

    public Optional<Address> findById(Long id) {
        return addressDao.findById(id);
    }

    public void createAddress(Address address) {
        addressDao.create(address);
    }

    public void updateAddress(Address address) {
        addressDao.update(address);
    }

    public void deleteAddressById(Long id) {
        addressDao.delete(id);
    }
}
