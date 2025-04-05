package org.eureka.stockAnalytics.exception;

import org.eureka.stockAnalytics.entity.crud.Person;

public class CrudException extends Exception {

    public CrudException(String message, Person person) {
        super(message);
    }
}
