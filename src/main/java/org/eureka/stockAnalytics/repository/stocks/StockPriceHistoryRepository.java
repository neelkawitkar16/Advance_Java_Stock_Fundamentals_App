package org.eureka.stockAnalytics.repository.stocks;

import org.eureka.stockAnalytics.entity.stocks.StockPriceHistory;
import org.eureka.stockAnalytics.vo.StockPriceHistoryKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StockPriceHistoryRepository extends JpaRepository<StockPriceHistory, StockPriceHistoryKey> {
}
