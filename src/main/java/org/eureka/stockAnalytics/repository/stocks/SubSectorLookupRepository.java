package org.eureka.stockAnalytics.repository.stocks;

import org.eureka.stockAnalytics.entity.stocks.SubSectorLookup;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubSectorLookupRepository extends JpaRepository<SubSectorLookup, Integer> {
}
