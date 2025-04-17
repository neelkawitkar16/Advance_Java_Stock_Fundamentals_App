package org.eureka.stockAnalytics.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.eureka.stockAnalytics.dto.StateMarketCapDTO;
import org.eureka.stockAnalytics.dto.StockFundamentalsWithPriceHistoryDTO;
import org.eureka.stockAnalytics.entity.stocks.SectorLookup;
import org.eureka.stockAnalytics.entity.stocks.StockPriceHistory;
import org.eureka.stockAnalytics.entity.stocks.StocksFundamentals;
import org.eureka.stockAnalytics.entity.stocks.SubSectorLookup;
import org.eureka.stockAnalytics.exception.InvalidInputException;
import org.eureka.stockAnalytics.exception.StockNotFoundException;
import org.eureka.stockAnalytics.service.MarketAnalyticsService;
import org.eureka.stockAnalytics.vo.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

// http://localhost:65000/stockanalytics/swagger-ui/index.html#/
@RestController
@RequestMapping(value = "/stocks")
@Tag(name= "Stocks Database Controller", description = "Stocks Controller Service has multiple end points to fetch data from StocksDB. It provides stock fundamentals of US stocks")
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

    //Sector = 37 (TECHNOLOGY)
    @PostMapping(value = "/technologyFundamentals")
    public List<StockFundamentalsVO> getStockFundamentalsBySector(@RequestBody List<String> tickersList) {
        if (tickersList == null || tickersList.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Tickers list cannot be empty");
        }
        return marketAnalyticsService.getStockFundamentalsBySector(tickersList);
    }

    @GetMapping(value = "/allStockFundamentals")
    public List<StocksFundamentals> getAllStockFundamentals() {
        return marketAnalyticsService.getAllStockFundamentals();
    }

    @GetMapping(value = "/sectorLookup")
    public List<SectorLookup> getSectorLookup() {
        return marketAnalyticsService.getSectorLookup();
    }

    @GetMapping(value = "/subSectorLookup")
    public List<SubSectorLookup> getSubSectorLookup() {
        return marketAnalyticsService.getSubSectorLookup();
    }

    @GetMapping(value = "/selectedStockFundamentals")
    public List<StocksFundamentals> getStockFundamentalsByTickers(@RequestBody List<String> tickers) {
        return marketAnalyticsService.getStockFundamentalsByTickers(tickers);
    }

    @GetMapping("/marketCapByState")
    public List<StateMarketCapDTO> getMarketCapByState() {
        return marketAnalyticsService.getTotalMarketCapByState();
    }

    @GetMapping("/getSpecificStockFundamentals/{ticker}")
    public StocksFundamentals getSpecificStockFundamentals(@PathVariable String ticker) {
        Optional<StocksFundamentals> specificStockFundamentals = marketAnalyticsService.getSpecificStockFundamentals(ticker);
        if(!specificStockFundamentals.isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No such ticker symbol exist in the stock fundamentals" + ticker);
        } else {
            return specificStockFundamentals.get();
        }
    }

    @GetMapping(value = "/getSpecificStockPriceHistoryJPA/{tickerSymbol}")
    public StockPriceHistory getSpecificStockPriceHistoryJPA(@PathVariable String tickerSymbol,
                                                          @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate tradingDate){
        StockPriceHistoryKey stockPriceHistoryKey = new StockPriceHistoryKey();
        stockPriceHistoryKey.setTickerSymbol(tickerSymbol);
        stockPriceHistoryKey.setTradingDate(tradingDate);
        Optional<StockPriceHistory> specificStockPriceHistoryJPA = marketAnalyticsService.getSpecificStockPriceHistoryJPA(stockPriceHistoryKey);
        if(!specificStockPriceHistoryJPA.isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid input");
        } else {
            return specificStockPriceHistoryJPA.get();
        }
    }

    //Top stocks by each sector
    @GetMapping(value = "/getTopStockBySectorJPASql")
    public List<TopStockBySectorVO> getTopStockBySectorJPASql(){
        return marketAnalyticsService.getTopStockBySectorJPASql();
    }

    @GetMapping(value = "/getTop3StocksBySectorJPASql")
    public List<TopSectorVO> getTop3StocksBySectorJPASql(){
        return marketAnalyticsService.getTop3StocksBySectorJPASql();
    }

    @GetMapping(value = "/getTopNStocksJPASql/{num}")
    public List<StocksFundamentals> getTopNStocksJPASql(@PathVariable Integer num) {
        return marketAnalyticsService.getTopNStocksJPASql(num);
    }

    @GetMapping(value = "/getStocksNonNullCRJPQL")
    public List<StocksFundamentals> getStocksNonNullCRJPQL() {
        return marketAnalyticsService.getStocksNonNullCRJPQL();
    }

    @GetMapping(value = "/getTopNStocksByMarketCapJPQL/{num}")
    public List<StocksFundamentals> getTopNStocksByMarketCapJPQL(@PathVariable Integer num) {
        return marketAnalyticsService.getTopNStocksByMarketCapJPQL(num);
    }

    @GetMapping(value = "/getTopNStocksCriteriaAPI/{num}")
    public List<StocksFundamentals> getTopNStocksCriteriaAPI(@PathVariable Integer num) {
        return marketAnalyticsService.getTopNStocksCriteriaAPI(num);
    }

    @GetMapping(value = "/getHighestOpenPriceJPQL/{tickerSymbol}")
    public ResponseEntity<StockPriceHistory> getHighestOpenPriceJPQL(@PathVariable String tickerSymbol) {
        logger.info("Fetching highest open price for: {}", tickerSymbol);

        try {
            return ResponseEntity.ok(marketAnalyticsService.getHighestOpenPriceJPQL(tickerSymbol));
        } catch (StockNotFoundException e) {
            logger.error("Stock not found: {}", tickerSymbol);
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/stock-fundamentals-price-history/{tickerSymbol}/{fromDate}/{toDate}")
    public ResponseEntity<StockFundamentalsWithPriceHistoryDTO> getFundamentalsWithPriceHistory(
            @PathVariable String tickerSymbol,
            @PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate fromDate,
            @PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate toDate) {

        logger.info("Fetching fundamentals and price history for {} ({} to {})", tickerSymbol, fromDate, toDate);

        try {
            return ResponseEntity.ok(
                    marketAnalyticsService.getFundamentalsWithPriceHistory(tickerSymbol, fromDate, toDate)
            );
        } catch (StockNotFoundException e) {
            logger.error("Stock not found: {}", tickerSymbol);
            return ResponseEntity.notFound().build();
        } catch (InvalidInputException e) {
            logger.error("Invalid input: {}", e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping(value = "/getTopNCumulativeReturnFeign")
    @Operation(method = "Cumulative Returns", tags = {"ABC", "Test"}, description = "The service would return Stocks Fundamentals and Cumulative returns for stocks for particular duration.")
    @ApiResponse(responseCode = "400", description = "Returns HTTP Error Code 400 when the Request Params are invalid")
    @ApiResponse(responseCode = "200", description = "Returns HTTP Code 200 when the Request Params are valid")
    @ApiResponse(responseCode = "500", description = "Opps! The server or the Database is down")
    public List<StockFundamentalsVO> getTopNCumulativeReturnFeign(@RequestParam Integer num,
                                                                  @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate fromDate,
                                                             @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate toDate,
                                                             @RequestParam BigDecimal marketCap) {
        return marketAnalyticsService.getCumulativeReturnFeign(num, fromDate, toDate, marketCap);
    }

    @GetMapping("/top-by-subsector")
    public List<SubsectorTopStocksVO> getTopStocksBySubsector(
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate fromDate,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate toDate,
            @RequestParam(required = false) BigDecimal minMarketCap) {
        return marketAnalyticsService.getTopStocksBySubsector(fromDate, toDate, minMarketCap);
    }

  /*  @GetMapping(value = "/stockPriceHistory")
    public List<StockPriceHistory> getStockPriceHistory(@RequestBody List<String> tickers) {
        return marketAnalyticsService.getStockPriceHistory(tickers);
    }*/

    // Dealing with some standard exception, to send the same response code
    @ExceptionHandler({IllegalArgumentException.class, SQLException.class, NullPointerException.class})
    public ResponseEntity generateExceptionResponse(Exception e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }
}