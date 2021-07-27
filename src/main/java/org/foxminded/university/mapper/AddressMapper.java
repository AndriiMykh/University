package org.foxminded.university.mapper;

import org.foxminded.university.dto.AddressDto;
import org.foxminded.university.entity.Address;

public class AddressMapper {
    public static AddressDto addressToAddressDto(Address address){
        if (address != null){
            AddressDto addressDto = AddressDto.builder()
                    .withId(address.getId())
                    .withCity(address.getCity())
                    .withStreet(address.getStreet())
                    .withFlatNumber(address.getFlatNumber())
                    .build();
            return addressDto;
        }else{
            return AddressDto.builder().build();
        }
    }

    public static Address addressDtoToAddress(AddressDto addressDto){
        if (addressDto != null){
            Address address = Address.builder()
                    .withId(addressDto.getId())
                    .withCity(addressDto.getCity())
                    .withStreet(addressDto.getStreet())
                    .withFlatNumber(addressDto.getFlatNumber())
                    .build();
            return address;
        }else{
            return Address.builder().build();
        }
    }
}
