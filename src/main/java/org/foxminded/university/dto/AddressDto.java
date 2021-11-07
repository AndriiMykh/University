package org.foxminded.university.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder(setterPrefix = "with")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class AddressDto {
    @EqualsAndHashCode.Include
    private Long id;
    @EqualsAndHashCode.Include
    private String city;
    @EqualsAndHashCode.Include
    private String street;
    @EqualsAndHashCode.Include
    private int flatNumber;
}
