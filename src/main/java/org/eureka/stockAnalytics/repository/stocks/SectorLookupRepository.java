package org.eureka.stockAnalytics.repository.stocks;

import org.eureka.stockAnalytics.entity.stocks.SectorLookup;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SectorLookupRepository extends JpaRepository<SectorLookup, Integer> {
}
