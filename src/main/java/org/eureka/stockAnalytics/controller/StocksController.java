package org.eureka.stockAnalytics.controller;

import org.eureka.stockAnalytics.service.MarketAnalyticsService;
import org.eureka.stockAnalytics.vo.StockPriceHistoryRequest;
import org.eureka.stockAnalytics.vo.StockPriceHistoryVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/stocks")
public class StocksController {

    @Autowired
    private MarketAnalyticsService marketAnalyticsService;

    @GetMapping(value = "/SpecificStockPriceHistory/{ticker}")
    public List<StockPriceHistoryVO> getSpecificStockPriceHistory(@PathVariable(value = "ticker") String tickerSymbol,
                                                                  @RequestParam(value = "fromDate") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate fromDate,
                                                                  @RequestParam(value = "toDate") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate toDate) {

        return marketAnalyticsService.getSpecificStockPriceHistory(tickerSymbol, fromDate, toDate);
    }

/*    @GetMapping(value = "/multipleStocksPriceHistory")
    public List<StockPriceHistoryVO> getMultipleStocksPriceHistory(@RequestBody StockPriceHistoryRequest stockPriceHistoryRequest) {
        return marketAnalyticsService.getMultipleStocksPriceHistory(stockPriceHistoryRequest.getTickersList(),
                stockPriceHistoryRequest.getFromDate(),
                stockPriceHistoryRequest.getToDate());*/

    @GetMapping(value = "/multipleStocksPriceHistory")
    public Map<String, List<StockPriceHistoryVO>> getMultipleStocksPriceHistory(@RequestBody StockPriceHistoryRequest stockPriceHistoryRequest){
        List<StockPriceHistoryVO> multipleStocksPriceHistory = marketAnalyticsService.getMultipleStocksPriceHistory(stockPriceHistoryRequest.getTickersList(),
                stockPriceHistoryRequest.getFromDate(),
                stockPriceHistoryRequest.getToDate());

        return multipleStocksPriceHistory.stream()
                .collect(Collectors.groupingBy(StockPriceHistoryVO::getTickerSymbol));
    }
}