package org.foxminded.university.entity;

import java.util.Date;
import java.util.Objects;

public abstract class Person {
    private final Long id;
    private final String firstName;
    private final String lastName;
    private final Date birthDate;
    private final String phoneNumber;
    private final String email;
    private final String password;
    private final Address address;

    protected Person(PersonBuilder<? extends PersonBuilder> personPersonBuilder) {
        this.id = personPersonBuilder.id;
        this.firstName = personPersonBuilder.firstName;
        this.lastName = personPersonBuilder.lastName;
        this.birthDate = personPersonBuilder.birthDate;
        this.phoneNumber = personPersonBuilder.phoneNumber;
        this.email = personPersonBuilder.email;
        this.password = personPersonBuilder.password;
        this.address = personPersonBuilder.address;
    }

    public Long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public Address getAddress() {
        return address;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Person person = (Person) o;
        return id.equals(person.id) &&
                firstName.equals(person.firstName) &&
                lastName.equals(person.lastName) &&
                birthDate.equals(person.birthDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstName, lastName, birthDate);
    }

    @Override
    public String toString() {
        return "{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", birthDate=" + birthDate +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", address=" + address;
    }

    public static class PersonBuilder<SELF extends PersonBuilder<SELF>> {
        private Long id;
        private String firstName;
        private String lastName;
        private Date birthDate;
        private String phoneNumber;
        private Address address;
        private String password;
        private String email;

        protected PersonBuilder() {
        }

        @SuppressWarnings("unchecked")
        public SELF self() {
            return (SELF) this;
        }

        public SELF withId(Long id) {
            this.id = id;
            return self();
        }

        public SELF withFirstName(String firstName) {
            this.firstName = firstName;
            return self();
        }

        public SELF withLastName(String lastName) {
            this.lastName = lastName;
            return self();
        }

        public SELF withBirthDate(Date birthDate) {
            this.birthDate = birthDate;
            return self();
        }

        public SELF withPhoneNumber(String phoneNumber) {
            this.phoneNumber = phoneNumber;
            return self();
        }

        public SELF withAddress(Address address) {
            this.address = address;
            return self();
        }

        public SELF withEmail(String email) {
            this.email = email;
            return self();
        }

        public SELF withPassword(String password) {
            this.password = password;
            return self();
        }
    }
}
