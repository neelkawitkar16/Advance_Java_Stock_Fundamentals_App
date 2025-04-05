package org.eureka.stockAnalytics.controller;

import org.eureka.stockAnalytics.entity.crud.Address;
import org.eureka.stockAnalytics.entity.stocks.StocksFundamentals;
import org.eureka.stockAnalytics.service.CrudService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import org.eureka.stockAnalytics.entity.crud.Person;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/crud")
public class CrudController {

    @Autowired
    private CrudService crudService;

    private static final Logger logger = LoggerFactory.getLogger(CrudController.class);

    @GetMapping(value = "/allPerson")
    public List<Person> getAllPersons() {
        return crudService.getAllPersons();
    }

    @GetMapping(value = "/allAddress")
    public List<Address> getAllAddress() {
        return crudService.getAllAddress();
    }

    @GetMapping(value = "/getPersonByID/{personID}")
    public Person getPersonByID(@PathVariable Integer personID) {
        Optional<Person> personByID = crudService.getPersonByID(personID);
        if (!personByID.isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid input");
        } else {
            return personByID.get();
        }
    }

    @PostMapping(value = "/insertPerson")
    public Person insertPerson(@RequestBody Optional<Person> personDetails) {
        if(personDetails.isEmpty())
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Request body is empty");
        return crudService.insertPerson(personDetails.get());
    }

    @DeleteMapping(value = "/deletePerson/{personID}")
    public void deletePerson(@PathVariable Integer personID) {
        crudService.deletePerson(personID);
    }

    @PutMapping(value = "/updatePerson")
    public Person updatePerson(@RequestBody Person person) {
        return crudService.updatePerson(person);
    }
}
