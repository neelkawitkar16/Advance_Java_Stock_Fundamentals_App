package org.eureka.stockAnalytics.repository.crud;

import org.springframework.data.jpa.repository.JpaRepository;
import org.eureka.stockAnalytics.entity.crud.Person;

public interface PersonRepository extends JpaRepository<Person, Integer> {
}
