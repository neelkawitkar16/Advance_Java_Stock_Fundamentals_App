package org.eureka.stockAnalytics.service;

import org.eureka.stockAnalytics.dao.LookupDAO;
import org.eureka.stockAnalytics.dao.StockFundamentalsDAO;
import org.eureka.stockAnalytics.dao.StockPriceHistoryDAO;
import org.eureka.stockAnalytics.dto.StateMarketCapDTO;
import org.eureka.stockAnalytics.dto.StockFundamentalsWithPriceHistoryDTO;
import org.eureka.stockAnalytics.entity.stocks.SectorLookup;
import org.eureka.stockAnalytics.entity.stocks.StockPriceHistory;
import org.eureka.stockAnalytics.entity.stocks.StocksFundamentals;
import org.eureka.stockAnalytics.entity.stocks.SubSectorLookup;
import org.eureka.stockAnalytics.exception.InvalidInputException;
import org.eureka.stockAnalytics.exception.StockNotFoundException;
import org.eureka.stockAnalytics.remote.StockCalculationClient;
import org.eureka.stockAnalytics.repository.stocks.SectorLookupRepository;
import org.eureka.stockAnalytics.repository.stocks.StockPriceHistoryRepository;
import org.eureka.stockAnalytics.repository.stocks.StocksFundamentalsRepository;
import org.eureka.stockAnalytics.repository.stocks.SubSectorLookupRepository;
import org.eureka.stockAnalytics.vo.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class MarketAnalyticsService {
    private StockPriceHistoryDAO stockPriceHistoryDAO;
    private LookupDAO lookupDAO;
    private StockFundamentalsDAO stockFundamentalsDAO;
    private StocksFundamentalsRepository stocksFundamentalsRepository;
    private SectorLookupRepository sectorLookupRepository;
    private SubSectorLookupRepository subSectorLookupRepository;
    private StockPriceHistoryRepository stockPriceHistoryRepository;
    private StockCalculationClient stockCalculationClient;
    private static final Logger logger = LoggerFactory.getLogger(MarketAnalyticsService.class);

    @Autowired //create obj of DAO and calling the constructor
    public MarketAnalyticsService(StockPriceHistoryDAO stockPriceHistoryDAO,
                                  LookupDAO lookupDAO,
                                  StockFundamentalsDAO stockFundamentalsDAO,
                                  StocksFundamentalsRepository stocksFundamentalsRepository,
                                  SectorLookupRepository sectorLookupRepository,
                                  SubSectorLookupRepository subSectorLookupRepository,
                                  StockPriceHistoryRepository stockPriceHistoryRepository,
                                  StockCalculationClient stockCalculationClient) {
        this.stockPriceHistoryDAO = stockPriceHistoryDAO;
        this.lookupDAO = lookupDAO;
        this.stockFundamentalsDAO = stockFundamentalsDAO;
        this.stocksFundamentalsRepository = stocksFundamentalsRepository;
        this.sectorLookupRepository = sectorLookupRepository;
        this.subSectorLookupRepository = subSectorLookupRepository;
        this.stockPriceHistoryRepository = stockPriceHistoryRepository;
        this.stockCalculationClient = stockCalculationClient;
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

    public List<StockFundamentalsVO> getALLStockFundamentalsVO() {
        return stockFundamentalsDAO.getAllStockFundamentalsFeign();
    }


    public List<StockFundamentalsVO> getStockFundamentalsBySector(List<String> tickersList) {
        return stockFundamentalsDAO.getStockFundamentalsBySector(tickersList);
    }

    public List<StocksFundamentals> getAllStockFundamentals() {
        return stocksFundamentalsRepository.findAll();
    }

    public List<SectorLookup> getSectorLookup() {
        return sectorLookupRepository.findAll();
    }

    public List<SubSectorLookup> getSubSectorLookup() {
        return subSectorLookupRepository.findAll();
    }

    public List<StocksFundamentals> getStockFundamentalsByTickers(List<String> tickers) {
        return stocksFundamentalsRepository.findAllById(tickers);
    }

    public List<StateMarketCapDTO> getTotalMarketCapByState() {
        return stocksFundamentalsRepository.getTotalMarketCapByState();
    }

    public Optional<StocksFundamentals> getSpecificStockFundamentals(String ticker) {
        return stocksFundamentalsRepository.findById(ticker);
    }

    public Optional<StockPriceHistory> getSpecificStockPriceHistoryJPA(StockPriceHistoryKey stockPriceHistoryKey) {
        return stockPriceHistoryRepository.findById(stockPriceHistoryKey);
    }

    public List<TopStockBySectorVO> getTopStockBySectorJPASql() {
        return stocksFundamentalsRepository.getTopStocksBySector();
    }

    public List<TopSectorVO> getTop3StocksBySectorJPASql() {
        List<TopStockBySectorVO> top3StocksBySector = stocksFundamentalsRepository.getTop3StocksBySector();

        List<TopSectorVO> finalOutputList = new ArrayList<>();

        Map<Integer, List<TopStockBySectorVO>> sectorWiseMap = top3StocksBySector.stream()
                .collect(Collectors.groupingBy(TopStockBySectorVO::getSectorID));
        //sector 34  - List<TopStockBySectorVO> 3
        sectorWiseMap.forEach((sectorID, topStockList) -> {
            TopSectorVO sectorVO = new TopSectorVO();
            //assigning sectorID
            sectorVO.setSectorID(sectorID);
            List<TopStockVO> topStockVOList = new ArrayList<>();
            //34 Apple - sector id, name, ticker sy AAPL, name, market
            topStockList.forEach(topStockBySectorVO -> {
                //assigning sectorName
                //Repetitive Step
                sectorVO.setSectorName(topStockBySectorVO.getSectorName());
                TopStockVO topStockVO = new TopStockVO();
                topStockVO.setTickerSymbol(topStockBySectorVO.getTickerSymbol());
                topStockVO.setTickerName(topStockBySectorVO.getTickerName());
                topStockVO.setMarketCap(topStockBySectorVO.getMarketCap());
                topStockVOList.add(topStockVO);
            });
            sectorVO.setTopStocks(topStockVOList);
            finalOutputList.add(sectorVO);
        });
        return finalOutputList;
    }

    public List<StocksFundamentals> getTopNStocksJPASql(Integer num) {
        return stocksFundamentalsRepository.getTopNStocks(num);
    }

    public List<StocksFundamentals> getStocksNonNullCRJPQL() {
        return stocksFundamentalsRepository.getStocksNonNullCR();
    }

    public List<StocksFundamentals> getTopNStocksByMarketCapJPQL(Integer num) {
        return stockFundamentalsDAO.getTopNStocksByMarketCapJPQL(num);
    }

    public List<StocksFundamentals> getTopNStocksCriteriaAPI(Integer num) {
        return stockFundamentalsDAO.getTopNStocksCriteriaAPI(num);
    }

    public StockPriceHistory getHighestOpenPriceJPQL(String tickerSymbol) {

        if (tickerSymbol == null || tickerSymbol.isBlank()) {
            logger.error("Invalid ticker symbol provided");
            throw new InvalidInputException("Ticker symbol cannot be empty");
        }
        return stockPriceHistoryRepository.findHighestOpenPriceByTickerSymbol(tickerSymbol)
                .orElseThrow(() -> {
                    logger.warn("Ticker symbol not found: {}", tickerSymbol);
                    return new StockNotFoundException("Stock not found with ticker: " + tickerSymbol);
                });
    }

    public StockFundamentalsWithPriceHistoryDTO getFundamentalsWithPriceHistory(
            String tickerSymbol, LocalDate fromDate, LocalDate toDate) {

        if (tickerSymbol == null || tickerSymbol.isBlank()) {
            logger.error("Invalid ticker symbol provided");
            throw new InvalidInputException("Ticker symbol cannot be empty");
        }
        if(fromDate.isAfter(toDate)) {
            throw new InvalidInputException("From date cannot be after to date");
        }

        //Fetch data
        StocksFundamentals stocksFundamentals = stocksFundamentalsRepository.findById(tickerSymbol)
                .orElseThrow(() -> {
                    logger.error("Stock not found: {}", tickerSymbol);
                    return new StockNotFoundException("Stock not found with the ticker: " + tickerSymbol);
                });

        List<StockPriceHistoryVO> priceHistoryList = stockPriceHistoryDAO
                .getSpecificStockPriceHistory(tickerSymbol, fromDate, toDate);

        return new StockFundamentalsWithPriceHistoryDTO(stocksFundamentals.getMarketCap(),
                stocksFundamentals.getCurrentRatio(),
                priceHistoryList);
    }

    public List<StockFundamentalsVO> getCumulativeReturnFeign(Integer num, LocalDate fromDate, LocalDate toDate, BigDecimal marketCap) {
        List<StockFundamentalsVO> allStockFundamentals = getALLStockFundamentalsVO();
        CRSRequestVO crsRequestVO = new CRSRequestVO();

        if(allStockFundamentals == null || allStockFundamentals.isEmpty()){
            throw new StockNotFoundException("The Database is down");
        }

        //Junit for HappyPath scenario
        List<StockFundamentalsVO> outputList = allStockFundamentals.stream()
                .filter(stockFundamentals -> stockFundamentals.getMarketCap() != null)
                .filter(stockFundamentals -> stockFundamentals.getMarketCap().compareTo(marketCap) > 0)
                .collect(Collectors.toList());

        // Junit to test DB is down scenario
        if (outputList.isEmpty()) {
            throw new StockNotFoundException("Stocks Database is Down");
        }

        List<String> tickersList = outputList.stream()
                .map(StockFundamentalsVO::getTickerSymbol)
                .collect(Collectors.toList());

        crsRequestVO.setTickers(tickersList);

        List<CRSResponseVO> cumulativeReturn = stockCalculationClient.getCumulativeReturn(fromDate, toDate, crsRequestVO);

        // Junit to test webservice is down scenario
        if(cumulativeReturn.isEmpty()) {
            throw new StockNotFoundException("Stock calculation Webservice is down");
        }

        Map<String, BigDecimal> cumulativeReturnMap = cumulativeReturn.stream()
                .collect(Collectors.toMap(CRSResponseVO::getTicker,
                        CRSResponseVO::getCumulativeReturn));

        outputList.forEach(stockFundamentals -> {
            stockFundamentals.setCumulativeReturn(cumulativeReturnMap.get(stockFundamentals.getTickerSymbol()));
        });

        // Junit to test Top N Stocks
        List<StockFundamentalsVO> sortedOutputList = outputList.stream()
                .filter(stock -> stock.getCumulativeReturn() != null)
                .sorted(Comparator.comparing(StockFundamentalsVO::getCumulativeReturn).reversed())
                .limit(num)
                .collect(Collectors.toList());

        return sortedOutputList;
    }

    public List<SubsectorTopStocksVO> getTopStocksBySubsector(LocalDate fromDate, LocalDate toDate, BigDecimal minMarketCap) {
        // 1. Get all stocks with their fundamentals
        List<StockFundamentalsVO> allStocks = getALLStockFundamentalsVO();

        // 2. Filter by market cap if provided
        if (minMarketCap != null) {
            allStocks = allStocks.stream()
                    .filter(stock -> stock.getMarketCap() != null &&
                            stock.getMarketCap().compareTo(minMarketCap) >= 0)
                    .collect(Collectors.toList());
        }

        // 3. Prepare request for cumulative returns
        CRSRequestVO crsRequest = new CRSRequestVO();
        crsRequest.setTickers(allStocks.stream()
                .map(StockFundamentalsVO::getTickerSymbol)
                .collect(Collectors.toList()));

        // 4. Get cumulative returns
        List<CRSResponseVO> cumulativeReturns = stockCalculationClient
                .getCumulativeReturn(fromDate, toDate, crsRequest);

        // 5. Create a map of ticker to cumulative return
        Map<String, BigDecimal> returnMap = cumulativeReturns.stream()
                .collect(Collectors.toMap(
                        CRSResponseVO::getTicker,
                        CRSResponseVO::getCumulativeReturn));

        // 6. Group by subsector and get top 5 stocks per subsector
        Map<String, List<StockFundamentalsVO>> stocksBySubsector = allStocks.stream()
                .filter(stock -> stock.getSubSectorID() != null && stock.getSectorID() != null)
                .collect(Collectors.groupingBy(StockFundamentalsVO::getSubSectorName));

        // 7. Process each subsector
        List<SubsectorTopStocksVO> result = new ArrayList<>();

        stocksBySubsector.forEach((subsector, stocks) -> {
            // Get sector name (assuming all stocks in subsector have same sector)
            String sector = stocks.get(0).getSectorName();

            // Sort by cumulative return descending and take top 5
            List<TopStockWithReturnVO> topStocks = stocks.stream()
                    .filter(stock -> returnMap.containsKey(stock.getTickerSymbol()))
                    .sorted((s1, s2) -> returnMap.get(s2.getTickerSymbol())
                            .compareTo(returnMap.get(s1.getTickerSymbol())))
                    .limit(5)
                    .map(stock -> new TopStockWithReturnVO(
                            stock.getTickerSymbol(),
                            stock.getTickerName(),
                            stock.getMarketCap(),
                            returnMap.get(stock.getTickerSymbol())))
                    .collect(Collectors.toList());

            if (!topStocks.isEmpty()) {
                result.add(new SubsectorTopStocksVO(sector, subsector, topStocks));
            }
        });
        return result;
    }
}
