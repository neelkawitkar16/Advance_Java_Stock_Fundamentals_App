package org.eureka.stockAnalytics.service;

import org.eureka.stockAnalytics.entity.crud.Address;
import org.eureka.stockAnalytics.exception.CrudException;
import org.eureka.stockAnalytics.repository.crud.AddressRepository;
import org.eureka.stockAnalytics.repository.crud.PersonRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.eureka.stockAnalytics.entity.crud.Person;

import java.util.List;
import java.util.Optional;

@Service
public class CrudService {
    private PersonRepository personRepository;
    private AddressRepository addressRepository;
    private static final Logger logger = LoggerFactory.getLogger(CrudService.class);

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

    public Optional<Person> getPersonByID(Integer personID) {
        return personRepository.findById(personID);
    }

    //json ignore -> so undoing it
    public Person insertPerson(Person personDetails) {
        List<Address> addressList = personDetails.getAddressList();
        addressList.forEach(address -> {
            address.setPerson(personDetails);
        });
        personDetails.setAddressList(addressList);

        return personRepository.save(personDetails);
    }

    public void deletePerson(Integer personID) {
       if(personRepository.existsById(personID)) {
           personRepository.deleteById(personID);
        boolean deleted =personRepository.existsById(personID);
        logger.info("Is the Person Deleted from Database: " + deleted);
       }
    }

    public Person updatePerson(Person person) {
        if(personRepository.existsById(person.getPersonId())) {
            return insertPerson(person);
        } else {
            throw new CrudException("No such person exist in CrudDB", person);
        }
    }

}
