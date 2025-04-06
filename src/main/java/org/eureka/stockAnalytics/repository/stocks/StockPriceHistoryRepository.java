package org.eureka.stockAnalytics.repository.stocks;

import org.eureka.stockAnalytics.entity.stocks.StockPriceHistory;
import org.eureka.stockAnalytics.vo.StockPriceHistoryKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StockPriceHistoryRepository extends JpaRepository<StockPriceHistory, StockPriceHistoryKey> {
    @Query(value = """
                select
                    sph
                from
                    StockPriceHistory sph
                where
                    sph.id.tickerSymbol = :tickerSymbol
                order by
                    sph.openPrice desc
                limit 1
            """)

    /*@Query(value = """
        SELECT * FROM endeavour.stocks_price_history 
        WHERE ticker_symbol = :tickerSymbol 
        ORDER BY open_price DESC 
        LIMIT 1
        """, nativeQuery = true)*/
    public Optional<StockPriceHistory> findHighestOpenPriceByTickerSymbol(@Param(value = "tickerSymbol") String tickerSymbol);
}
