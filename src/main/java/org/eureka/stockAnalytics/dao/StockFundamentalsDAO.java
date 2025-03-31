package org.eureka.stockAnalytics.dao;

import org.eureka.stockAnalytics.vo.StockFundamentalsVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class StockFundamentalsDAO {

    @Autowired
    NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public List<StockFundamentalsVO> getStockFundamentals(List<String> tickersList) {
        String sqlQuery = """
                SELECT 
                        sf.*,
                        ssl.subsector_name,
                        sl.sector_name,
                        sl2.ticker_name
                    FROM 
                        endeavour.stock_fundamentals sf
                    LEFT JOIN 
                        endeavour.subsector_lookup ssl
                     ON 
                        sf.subsector_id=ssl.subsector_id
                    LEFT JOIN 
                        endeavour.sector_lookup sl
                    ON 
                        sf.sector_id=sl.sector_id
                    LEFT JOIN 
                        endeavour.stocks_lookup sl2
                    ON 
                        sf.ticker_symbol=sl2.ticker_symbol
                    WHERE 
                        sf.ticker_symbol IN (:tickers)
                """;

        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("tickers", tickersList);

        List<StockFundamentalsVO> stockFundamentalsList = namedParameterJdbcTemplate.query(sqlQuery, mapSqlParameterSource, (rs, rowNum) -> {
            StockFundamentalsVO stockFundamentalsVO = new StockFundamentalsVO();
            stockFundamentalsVO.setTickerName(rs.getString("ticker_name"));
            stockFundamentalsVO.setTickerSymbol(rs.getString("ticker_symbol"));
            stockFundamentalsVO.setSectorID(rs.getInt("sector_id"));
            stockFundamentalsVO.setSectorName(rs.getString("sector_name"));
            stockFundamentalsVO.setSubSectorID(rs.getInt("subsector_id"));
            stockFundamentalsVO.setSubSectorName(rs.getString("sector_name"));
            stockFundamentalsVO.setMarketCap(rs.getBigDecimal("market_cap"));
            stockFundamentalsVO.setCurrentRatio(rs.getBigDecimal("current_ratio"));
            return stockFundamentalsVO;
        });
        return stockFundamentalsList;
    }
}
