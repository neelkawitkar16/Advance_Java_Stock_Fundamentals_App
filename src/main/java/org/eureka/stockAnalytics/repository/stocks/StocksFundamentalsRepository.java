package org.eureka.stockAnalytics.repository.stocks;

import org.eureka.stockAnalytics.dto.StateMarketCapDTO;
import org.eureka.stockAnalytics.entity.stocks.StocksFundamentals;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface StocksFundamentalsRepository extends JpaRepository<StocksFundamentals,String> {
    @Query("SELECT NEW org.eureka.stockAnalytics.dto.StateMarketCapDTO(" +
            "cl.state, SUM(sf.marketCap)) " +
            "FROM StocksFundamentals sf " +
            "JOIN sf.location cl " +
            "GROUP BY cl.state " +
            "ORDER BY SUM(sf.marketCap) DESC")
    List<StateMarketCapDTO> getTotalMarketCapByState();
}
