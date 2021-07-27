package org.foxminded.university.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import java.util.Date;

@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@SuperBuilder(setterPrefix = "with")
@NoArgsConstructor
@ToString
public abstract class PersonDto {

    @EqualsAndHashCode.Include
    private Long id;

    @EqualsAndHashCode.Include
    private String firstName;

    @EqualsAndHashCode.Include
    private String lastName;
    private Date birthDate;
    private String phoneNumber;
    private String email;
    private String password;
    private AddressDto address;
}
