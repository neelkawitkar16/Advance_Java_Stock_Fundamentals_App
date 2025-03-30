package org.eureka.stockAnalytics.rowMappers;

import org.eureka.stockAnalytics.vo.StockPriceHistoryVO;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class StockPriceHistoryRowMapper implements RowMapper<StockPriceHistoryVO> {

    @Override
    public StockPriceHistoryVO mapRow(ResultSet rs, int rowNum) throws SQLException {
        StockPriceHistoryVO stockPriceHistoryVO = new StockPriceHistoryVO();

        stockPriceHistoryVO.setTickerSymbol(rs.getString("ticker_symbol"));
        stockPriceHistoryVO.setTradingDate(rs.getDate("trading_date").toLocalDate());
        stockPriceHistoryVO.setClosePrice(rs.getBigDecimal("close_price"));
        stockPriceHistoryVO.setOpenPrice(rs.getBigDecimal("open_price"));
        stockPriceHistoryVO.setHighPrice(rs.getBigDecimal("high_price"));
        stockPriceHistoryVO.setLowPrice(rs.getBigDecimal("low_price"));
        stockPriceHistoryVO.setVolume(rs.getBigDecimal("volume"));

        return stockPriceHistoryVO;
    }
}
