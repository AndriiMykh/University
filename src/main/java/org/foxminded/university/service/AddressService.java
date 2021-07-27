package org.foxminded.university.service;

import lombok.AllArgsConstructor;
import org.foxminded.university.dao.AddressDao;
import org.foxminded.university.domain.Page;
import org.foxminded.university.domain.Pageable;
import org.foxminded.university.dto.AddressDto;
import org.foxminded.university.entity.Address;
import org.foxminded.university.entity.Student;
import org.foxminded.university.exception.ServiceException;
import org.foxminded.university.mapper.AddressMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.foxminded.university.mapper.AddressMapper.addressDtoToAddress;
import static org.foxminded.university.mapper.AddressMapper.addressToAddressDto;

@Service
@AllArgsConstructor
public class AddressService {
    private final AddressDao addressDao;

    public List<AddressDto> findAll() {
        return addressDao.findAll()
                .stream()
                .map(AddressMapper::addressToAddressDto)
                .collect(Collectors.toList());
    }

    public Pageable<AddressDto> findAll(Page page) {
        List<AddressDto> addressDtos = addressDao.findAll(page).getItems()
                .stream()
                .map(AddressMapper::addressToAddressDto)
                .collect(Collectors.toList());
        return new Pageable<>(addressDtos, page.getPageNumber(), page.getItemsPerPage());

    }

    public AddressDto findById(Long id) {
        Optional<Address> address = addressDao.findById(id);
        if(address.isEmpty()){
            throw new ServiceException("Address not found with id: "+ id);
        }
        return addressToAddressDto(address.get());
    }

    public void createAddress(AddressDto addressDto) {
        addressDao.create(addressDtoToAddress(addressDto));
    }

    public void updateAddress(AddressDto addressDto) {
        addressDao.update(addressDtoToAddress(addressDto));
    }

    public void deleteAddressById(Long id) {
        addressDao.delete(id);
    }
}
