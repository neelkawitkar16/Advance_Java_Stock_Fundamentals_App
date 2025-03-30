package org.eureka.stockAnalytics.service;

import org.eureka.stockAnalytics.dao.StockPriceHistoryDAO;
import org.eureka.stockAnalytics.vo.StockPriceHistoryVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class MarketAnalyticsService {
    private StockPriceHistoryDAO stockPriceHistoryDAO;

    @Autowired //create obj of DAO and calling the constructor
    public MarketAnalyticsService(StockPriceHistoryDAO stockPriceHistoryDAO) {
        this.stockPriceHistoryDAO = stockPriceHistoryDAO;
    }

    public List<StockPriceHistoryVO> getSpecificStockPriceHistory(String tickerSymbol, LocalDate fromDate, LocalDate toDate) {
        return stockPriceHistoryDAO.getSpecificStockPriceHistory(tickerSymbol, fromDate, toDate);
    }

    public List<StockPriceHistoryVO> getMultipleStocksPriceHistory(List<String> tickersList, LocalDate fromDate, LocalDate toDate) {
        return stockPriceHistoryDAO.getMultipleStocksPriceHistory(tickersList, fromDate, toDate);
    }
}
