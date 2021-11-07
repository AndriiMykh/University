package org.foxminded.university.mapper;

import org.foxminded.university.dto.AddressDto;
import org.foxminded.university.entity.Address;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.foxminded.university.mapper.AddressMapper.addressDtoToAddress;
import static org.foxminded.university.mapper.AddressMapper.addressToAddressDto;
import static org.junit.jupiter.api.Assertions.assertAll;

public class AddressMapperTest {

    @Test
    public void addressToAddressDtoShouldConvertAddressToAddressDto(){
        Address address = Address
                .builder()
                .withId(5L)
                .withCity("Kyiv")
                .withFlatNumber(5)
                .withStreet("Zhovtneva")
                .build();
        AddressDto addressDto = addressToAddressDto(address);
        assertAll(
                ()-> assertThat(addressDto.getId()).isEqualTo(address.getId()),
                ()-> assertThat(addressDto.getCity()).isEqualTo(address.getCity()),
                ()-> assertThat(addressDto.getFlatNumber()).isEqualTo(address.getFlatNumber()),
                ()-> assertThat(addressDto.getStreet()).isEqualTo(address.getStreet())
        );
    }

    @Test
    public void addressToAddressDtoShouldReturnEmptyDto(){
        assertThat(addressToAddressDto(null)).isEqualTo(AddressDto.builder().build());
    }

    @Test
    public void addressDtoToAddressShouldConvertAddressDtoToAddress(){
        AddressDto addressDto = AddressDto
                .builder()
                .withId(5L)
                .withCity("Kyiv")
                .withFlatNumber(5)
                .withStreet("Zhovtneva")
                .build();
        Address address = addressDtoToAddress(addressDto);
        assertAll(
                ()-> assertThat(address.getId()).isEqualTo(addressDto.getId()),
                ()-> assertThat(address.getCity()).isEqualTo(addressDto.getCity()),
                ()-> assertThat(address.getFlatNumber()).isEqualTo(addressDto.getFlatNumber()),
                ()-> assertThat(address.getStreet()).isEqualTo(addressDto.getStreet())
        );
    }

    @Test
    public void addressDtoToAddressShouldReturnEmptyAddress(){
        assertThat(addressDtoToAddress(null)).isEqualTo(Address.builder().build());
    }

}
