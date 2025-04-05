package org.eureka.stockAnalytics.entity.crud;

import jakarta.persistence.*;
import org.eureka.stockAnalytics.entity.crud.Address;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "person", schema = "endeavour_test_area")
public class Person {
    @Column(name = "person_id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // create id automatically
    private Integer personId;
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;
    @Column(name = "dob")
    private LocalDate dob;

    @OneToMany(mappedBy = "person", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Address> addressList;

    public List<Address> getAddressList() {
        return addressList;
    }

    public void setAddressList(List<Address> addressList) {
        this.addressList = addressList;
    }

    public Integer getPersonId() {
        return personId;
    }

    public void setPersonId(Integer personId) {
        this.personId = personId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public LocalDate getDob() {
        return dob;
    }

    public void setDob(LocalDate dob) {
        this.dob = dob;
    }

    @Override
    public String toString() {
        return "Person{" +
                "personId=" + personId +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", dob=" + dob +
                "}\n";
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof org.eureka.stockAnalytics.entity.crud.Person person)) return false;
        return Objects.equals(getPersonId(), person.getPersonId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getPersonId());
    }
}
