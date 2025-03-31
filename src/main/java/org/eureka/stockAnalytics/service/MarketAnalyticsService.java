package org.eureka.stockAnalytics.service;

import org.eureka.stockAnalytics.dao.LookupDAO;
import org.eureka.stockAnalytics.dao.StockFundamentalsDAO;
import org.eureka.stockAnalytics.dao.StockPriceHistoryDAO;
import org.eureka.stockAnalytics.vo.SectorVO;
import org.eureka.stockAnalytics.vo.StockFundamentalsVO;
import org.eureka.stockAnalytics.vo.StockPriceHistoryVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
public class MarketAnalyticsService {
    private StockPriceHistoryDAO stockPriceHistoryDAO;
    private LookupDAO lookupDAO;
    private StockFundamentalsDAO stockFundamentalsDAO;
    private static final Logger logger = LoggerFactory.getLogger(MarketAnalyticsService.class);

    @Autowired //create obj of DAO and calling the constructor
    public MarketAnalyticsService(StockPriceHistoryDAO stockPriceHistoryDAO,
                                  LookupDAO lookupDAO,
                                  StockFundamentalsDAO stockFundamentalsDAO) {
        this.stockPriceHistoryDAO = stockPriceHistoryDAO;
        this.lookupDAO = lookupDAO;
        this.stockFundamentalsDAO = stockFundamentalsDAO;
    }

    //Stock Price History related methods
    public List<StockPriceHistoryVO> getSpecificStockPriceHistory(String tickerSymbol,
                                                                  LocalDate fromDate,
                                                                  LocalDate toDate,
                                                                  Optional<String> sortField,
                                                                  Optional<String> sortDirection) {

        List<StockPriceHistoryVO> specificStockPriceHistoryList = stockPriceHistoryDAO.getSpecificStockPriceHistory(tickerSymbol, fromDate, toDate);

        String sort = sortField.orElse("tradingDate");
        String direction = sortDirection.orElse("asc");

        Comparator comparator = switch (sort.toUpperCase()) {
            case "CLOSEPRICE" -> Comparator.comparing(StockPriceHistoryVO::getClosePrice);
            case "OPENPRICE" -> Comparator.comparing(StockPriceHistoryVO::getOpenPrice);
            case "TRADINGDATE" -> Comparator.comparing(StockPriceHistoryVO::getTradingDate);
            case "VOLUME" -> Comparator.comparing(StockPriceHistoryVO::getVolume);
            default -> {
                logger.info("Invalid Sort Field");
                throw new IllegalArgumentException("Invalid Sort Field" + sort);
            }
        };

        if(direction.equalsIgnoreCase("DESC")) {
            comparator = comparator.reversed();
        }

        //Collections.sort(specificStockPriceHistoryList,comparator);
        specificStockPriceHistoryList.sort(comparator);

        return specificStockPriceHistoryList;
    }

    public List<StockPriceHistoryVO> getMultipleStocksPriceHistory(List<String> tickersList,
                                                                   LocalDate fromDate,
                                                                   LocalDate toDate) {
        return stockPriceHistoryDAO.getMultipleStocksPriceHistory(tickersList, fromDate, toDate);
    }

    // Sector related methods
    public SectorVO getSectorById(int sectorId) {
        return lookupDAO.getSectorById(sectorId);
    }

    public List<SectorVO> getAllSectors() {
        return lookupDAO.getAllSectors();
    }

    //Stock Fundamentals related methods
    public List<StockFundamentalsVO> getStockFundamentals(List<String> tickersList) {
        return stockFundamentalsDAO.getStockFundamentals(tickersList);
    }

}
