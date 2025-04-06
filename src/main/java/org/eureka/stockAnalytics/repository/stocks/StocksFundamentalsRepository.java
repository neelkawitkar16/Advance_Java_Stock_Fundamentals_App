package org.eureka.stockAnalytics.repository.stocks;

import org.eureka.stockAnalytics.dto.StateMarketCapDTO;
import org.eureka.stockAnalytics.entity.stocks.StocksFundamentals;
import org.eureka.stockAnalytics.vo.TopStockBySectorVO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface StocksFundamentalsRepository extends JpaRepository<StocksFundamentals,String> {
    @Query("SELECT NEW org.eureka.stockAnalytics.dto.StateMarketCapDTO(" +
            "cl.state, SUM(sf.marketCap)) " +
            "FROM StocksFundamentals sf " +
            "JOIN sf.location cl " +
            "GROUP BY cl.state " +
            "ORDER BY SUM(sf.marketCap) DESC")
    List<StateMarketCapDTO> getTotalMarketCapByState();

    @Query(name = "StocksFundamentals.TopStockBySector", nativeQuery = true)
    public List<TopStockBySectorVO> getTopStocksBySector();

    @Query(name = "StocksFundamentals.Top3StocksBySector", nativeQuery = true)
    public List<TopStockBySectorVO> getTop3StocksBySector();

    @Query(nativeQuery = true, value = """
                select
                        *
                    from
                        endeavour.stock_fundamentals sf
                    order by
                        sf.market_cap desc
                    limit :limit;
            """)
    public List<StocksFundamentals> getTopNStocks(@Param(value = "limit") Integer limit);

    @Query(value = """
                select
                        sf
                    from
                        StocksFundamentals sf
                    where
                        sf.currentRatio is not null
                    order by sf.marketCap desc
            """)
    public List<StocksFundamentals> getStocksNonNullCR();
}
