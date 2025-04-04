package org.eureka.stockAnalytics.repository.crud;

import org.eureka.stockAnalytics.entity.crud.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address, Integer> {
}
