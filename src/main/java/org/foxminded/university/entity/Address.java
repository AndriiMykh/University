package org.foxminded.university.entity;

import java.util.Objects;

public class Address {
    private final Long id;
    private final String city;
    private final String street;
    private final int flatNumber;

    private Address(Builder builder) {
        this.id = builder.id;
        this.city = builder.city;
        this.street = builder.street;
        this.flatNumber = builder.flatNumber;
    }

    public Long getId() {
        return id;
    }

    public String getCity() {
        return city;
    }

    public String getStreet() {
        return street;
    }

    public int getFlatNumber() {
        return flatNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Address address = (Address) o;
        return flatNumber == address.flatNumber &&
                Objects.equals(id, address.id) &&
                Objects.equals(city, address.city) &&
                Objects.equals(street, address.street);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, city, street, flatNumber);
    }


    public static class Builder {
        private Long id;
        private String city;
        private String street;
        private int flatNumber;

        private Builder() {
        }

        public Builder withId(Long id) {
            this.id = id;
            return Builder.this;
        }

        public Builder withCity(String city) {
            this.city = city;
            return Builder.this;
        }

        public Builder withStreet(String street) {
            this.street = street;
            return Builder.this;
        }

        public Builder withFlatNumber(int flatNumber) {
            this.flatNumber = flatNumber;
            return Builder.this;
        }

        public Address build() {
            return new Address(this);
        }
    }

    public static Builder builder() {
        return new Builder();
    }
}
