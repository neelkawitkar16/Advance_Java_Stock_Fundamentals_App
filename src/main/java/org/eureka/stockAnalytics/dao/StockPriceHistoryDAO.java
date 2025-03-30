package org.eureka.stockAnalytics.dao;

import org.eureka.stockAnalytics.rowMappers.StockPriceHistoryRowMapper;
import org.eureka.stockAnalytics.vo.StockPriceHistoryVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component  // Component -> DAO
public class StockPriceHistoryDAO {
    @Autowired
    JdbcTemplate jdbcTemplate;

    @Autowired
    NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public List<StockPriceHistoryVO> getSpecificStockPriceHistory(String tickerSymbol, LocalDate fromDate, LocalDate toDate) {
        String sqlQuery = """
                select 
                    *
                from 
                    endeavour.stocks_price_history sph
                where
                     ticker_symbol = ? 
                     and
                     trading_date between ? and ?
                """;
        Object[] inputParams = new Object[]{tickerSymbol, fromDate, toDate};
        List<StockPriceHistoryVO> specificPriceHistoryList = jdbcTemplate.query(sqlQuery, inputParams, new StockPriceHistoryRowMapper());
        return specificPriceHistoryList;
    }

    public List<StockPriceHistoryVO> getMultipleStocksPriceHistory(List<String> tickersList, LocalDate fromDate, LocalDate toDate) {
        String sqlQuery = """
                select 
                    *
                from 
                    endeavour.stocks_price_history sph
                where
                     ticker_symbol IN (:a) 
                     and
                     trading_date between :b and :c
                """;
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("a", tickersList);
        mapSqlParameterSource.addValue("b", fromDate);
        mapSqlParameterSource.addValue("c", toDate);

        List<StockPriceHistoryVO> multipleStocksPriceHistoryList =
                namedParameterJdbcTemplate.query(sqlQuery, mapSqlParameterSource,
                        ((rs, rowNum) -> {
                            StockPriceHistoryVO stockPriceHistoryVO = new StockPriceHistoryVO();
                            stockPriceHistoryVO.setTickerSymbol(rs.getString("ticker_symbol"));
                            stockPriceHistoryVO.setTradingDate(rs.getDate("trading_date").toLocalDate());
                            stockPriceHistoryVO.setClosePrice(rs.getBigDecimal("close_price"));
                            stockPriceHistoryVO.setHighPrice(rs.getBigDecimal("high_price"));
                            stockPriceHistoryVO.setLowPrice(rs.getBigDecimal("low_price"));
                            stockPriceHistoryVO.setOpenPrice(rs.getBigDecimal("open_price"));
                            stockPriceHistoryVO.setVolume(rs.getBigDecimal("volume"));
                            return stockPriceHistoryVO;
                        }));
        return multipleStocksPriceHistoryList;
    }
}
