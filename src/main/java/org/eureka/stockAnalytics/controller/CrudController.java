package org.eureka.stockAnalytics.controller;

import org.eureka.stockAnalytics.entity.crud.Address;
import org.eureka.stockAnalytics.entity.stocks.StocksFundamentals;
import org.eureka.stockAnalytics.service.CrudService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.eureka.stockAnalytics.entity.crud.Person;

import java.util.List;

@RestController
@RequestMapping(value = "/crud")
public class CrudController {

    @Autowired
    private CrudService crudService;

    @GetMapping(value = "/allPerson")
    public List<Person> getAllPersons() {
        return crudService.getAllPersons();
    }

    @GetMapping(value = "/allAddress")
    public List<Address> getAllAddress() {
        return crudService.getAllAddress();
    }
}
