package org.eureka.stockAnalytics.service;

import org.eureka.stockAnalytics.entity.crud.Address;
import org.eureka.stockAnalytics.repository.crud.AddressRepository;
import org.eureka.stockAnalytics.repository.crud.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.eureka.stockAnalytics.entity.crud.Person;

import java.util.List;

@Service
public class CrudService {
    private PersonRepository personRepository;
    private AddressRepository addressRepository;

    @Autowired
    public CrudService(PersonRepository personRepository, AddressRepository addressRepository) {
        this.personRepository = personRepository;
        this.addressRepository = addressRepository;
    }

    public List<Person> getAllPersons() {
        return personRepository.findAll();
    }

    public List<Address> getAllAddress() {
        return addressRepository.findAll();
    }
}
