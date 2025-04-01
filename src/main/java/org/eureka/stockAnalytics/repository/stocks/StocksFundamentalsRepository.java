package org.eureka.stockAnalytics.repository.stocks;

import org.eureka.stockAnalytics.entity.stocks.StocksFundamentals;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StocksFundamentalsRepository extends JpaRepository<StocksFundamentals,String> {

}
