package org.eureka.stockAnalytics.controller;

import org.eureka.stockAnalytics.service.MarketAnalyticsService;
import org.eureka.stockAnalytics.vo.SectorVO;
import org.eureka.stockAnalytics.vo.StockFundamentalsVO;
import org.eureka.stockAnalytics.vo.StockPriceHistoryRequest;
import org.eureka.stockAnalytics.vo.StockPriceHistoryVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/stocks")
public class StocksController {

    @Autowired
    private MarketAnalyticsService marketAnalyticsService;

    private static final Logger logger = LoggerFactory.getLogger(StocksController.class);

    @GetMapping(value = "/SpecificStockPriceHistory/{ticker}")
    public List<StockPriceHistoryVO> getSpecificStockPriceHistory(@PathVariable(value = "ticker") String tickerSymbol,
                                                                  @RequestParam(value = "fromDate") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate fromDate,
                                                                  @RequestParam(value = "toDate") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate toDate,
                                                                  @RequestParam(value="sortField") Optional<String> sortField,
                                                                  @RequestParam(value="sortDirection") Optional<String> sortDirection) {
        logger.info("In method getSpecificStockPriceHistory()");
        if(fromDate.isAfter(toDate)) {
            logger.debug("The fromDate : {} passed is before toDate: {}" ,fromDate,toDate);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "the fromDate passed is before toDate");
        }
        return marketAnalyticsService.getSpecificStockPriceHistory(tickerSymbol, fromDate, toDate, sortField, sortDirection);
    }

    @GetMapping(value = "/multipleStocksPriceHistory")
    public Map<String, List<StockPriceHistoryVO>> getMultipleStocksPriceHistory(@RequestBody StockPriceHistoryRequest stockPriceHistoryRequest){
        // Request validation
        if (stockPriceHistoryRequest.getTickersList().isEmpty() ||
                stockPriceHistoryRequest.getFromDate().isAfter(stockPriceHistoryRequest.getToDate())) {
            logger.debug("The REQUEST BODY sent is: " + stockPriceHistoryRequest.toString());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid Request Body");
        }
        //Calling the market analytics service
        List<StockPriceHistoryVO> multipleStocksPriceHistory = marketAnalyticsService.getMultipleStocksPriceHistory(stockPriceHistoryRequest.getTickersList(),
                stockPriceHistoryRequest.getFromDate(),
                stockPriceHistoryRequest.getToDate());

        return multipleStocksPriceHistory.stream()
                .collect(Collectors.groupingBy(StockPriceHistoryVO::getTickerSymbol));
    }

    // Sector endpoints
    @GetMapping(value = "/sectors/{sector-id}")
    public SectorVO getSectorById(@PathVariable(value = "sector-id") int sectorId) {
        logger.info("In method getSectorById()");
        return marketAnalyticsService.getSectorById(sectorId);
    }

    @GetMapping("/sectors")
    public List<SectorVO> getAllSectors() {
        logger.info("In method getAllSectors()");
        return marketAnalyticsService.getAllSectors();
    }

    @PostMapping(value = "/stockFundamentals")
    public List<StockFundamentalsVO> getStockFundamentals(@RequestBody List<String> tickersList) {
        if (tickersList.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Tickers List in Request Body is Empty");
        }
        return marketAnalyticsService.getStockFundamentals(tickersList);
    }

    // Dealing with some standard exception, to send the same response code
    @ExceptionHandler({IllegalArgumentException.class, SQLException.class, NullPointerException.class})
    public ResponseEntity generateExceptionResponse(Exception e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }

}