package org.eureka.stockAnalytics.service;

import org.eureka.stockAnalytics.dao.StockFundamentalsDAO;
import org.eureka.stockAnalytics.exception.StockNotFoundException;
import org.eureka.stockAnalytics.remote.StockCalculationClient;
import org.eureka.stockAnalytics.vo.CRSResponseVO;
import org.eureka.stockAnalytics.vo.StockFundamentalsVO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
public class MarketAnalyticsServiceTest {
    @Autowired
    private MarketAnalyticsService marketAnalyticsService;

    @MockBean
    private StockFundamentalsDAO stockFundamentalsDAO;

    @MockBean
    private StockCalculationClient stockCalculationClient;

    // Junit to test happy path scenario
    @Test
    public void getCumulativeReturnFeignTest() {
        List<StockFundamentalsVO> dummyTestList = List.of(
                createStockFundamentalsVO("AAPL", new BigDecimal(0.05), new BigDecimal(34344545)),
                createStockFundamentalsVO("TSLA", new BigDecimal(0.34), new BigDecimal(344)),
                createStockFundamentalsVO("ABC", new BigDecimal(0.045), new BigDecimal(3434340)),
                createStockFundamentalsVO("MSFT", new BigDecimal(0.345), new BigDecimal(96700))
        );

        //Mockito framework is used to create mock beans of external dependency and mimic the behavior with provided output
        //Stubbing DB call and returning dummyList
        Mockito.when(stockFundamentalsDAO.getAllStockFundamentalsFeign()).thenReturn(dummyTestList);

        List<CRSResponseVO> dummyResponseList = List.of(
                createResponseObjects("AAPL", new BigDecimal(9.5)),
                createResponseObjects("TSLA", new BigDecimal(10.5)),
                createResponseObjects("ABC", new BigDecimal(8.5)),
                createResponseObjects("MSFT", new BigDecimal(12.5))
        );

        //Stubbing Webservice call and returning dummy list
        Mockito.when(stockCalculationClient.getCumulativeReturn(Mockito.any(), Mockito.any(), Mockito.any()))
                .thenReturn(dummyResponseList);

        List<StockFundamentalsVO> outputList = marketAnalyticsService.getCumulativeReturnFeign(5, LocalDate.now().minusMonths(6),
                LocalDate.now(),
                new BigDecimal(0));

        Assertions.assertNotNull(outputList);
        Assertions.assertEquals(5, outputList.size());

//        Assertions.assertEquals(dummyResponseList.get(0).getCumulativeReturn(), outputList);

    }

    @Test
    public void getCumulativeReturnFeignLimitTest() {

        List<StockFundamentalsVO> dummyTestList = List.of(
                createStockFundamentalsVO("AAPL", new BigDecimal(0.05), new BigDecimal(44545)),
                createStockFundamentalsVO("TSLA", new BigDecimal(0.34), new BigDecimal(23444)),
                createStockFundamentalsVO("ABC", new BigDecimal(0.045), new BigDecimal(3434340)),
                createStockFundamentalsVO("MSFT", new BigDecimal(0.345), new BigDecimal(96700)));

        Mockito.when(stockFundamentalsDAO.getAllStockFundamentalsFeign()).thenReturn(dummyTestList);

        List<CRSResponseVO> dummyResponseList = List.of(
                createResponseObjects("AAPL", new BigDecimal(9.5)),
                createResponseObjects("TSLA", new BigDecimal(10.5)),
                createResponseObjects("ABC", new BigDecimal(8.5)),
                createResponseObjects("MSFT", new BigDecimal(12.5))
        );

        Mockito.when(stockCalculationClient.getCumulativeReturn(Mockito.any(), Mockito.any(), Mockito.any()))
                .thenReturn(dummyResponseList);

        List<StockFundamentalsVO> outputList = marketAnalyticsService.getCumulativeReturnFeign(3, LocalDate.now().minusMonths(6), LocalDate.now(), new BigDecimal(0));

        Assertions.assertEquals(3, outputList.size());

    }

    @Test
    public void getCumulativeReturnFeignLimitTestForException() {
        Mockito.when(stockFundamentalsDAO.getAllStockFundamentalsFeign()).thenReturn(null);

        StockNotFoundException stockNotFoundException = Assertions.assertThrows(StockNotFoundException.class, () -> marketAnalyticsService.getCumulativeReturnFeign(3, LocalDate.now().minusMonths(6),
                LocalDate.now(),
                new BigDecimal(0)));

        Assertions.assertEquals("The Database is down", stockNotFoundException.getMessage());
    }


    @Test
    public void getCumulativeReturnFeignTestForWebServiceException(){
        List<StockFundamentalsVO> dummyTestList = List.of(
                createStockFundamentalsVO("AAPL", new BigDecimal(0.05), new BigDecimal(9865400)),
                createStockFundamentalsVO("TSLA", new BigDecimal(0.55), new BigDecimal(98000)),
                createStockFundamentalsVO("NVDA", new BigDecimal(0.45), new BigDecimal(985600)),
                createStockFundamentalsVO("ABC", new BigDecimal(0.95), new BigDecimal(989800)),
                createStockFundamentalsVO("TEMP", new BigDecimal(1.05), new BigDecimal(980230)));

        //Mockito Framework is used to create Mock Beans of external Dependency and mimic the behaviour with provided Output
        //Stubbing DataBase Call and returning dummyList
        Mockito.when(stockFundamentalsDAO.getAllStockFundamentalsFeign()).thenReturn(dummyTestList);

        //Stubbing Webservice call and returning dummy list
        Mockito.when(stockCalculationClient.getCumulativeReturn(Mockito.any(), Mockito.any(), Mockito.any()))
                .thenReturn(new ArrayList<>());

        StockNotFoundException stockException = Assertions.assertThrows(StockNotFoundException.class, () -> marketAnalyticsService.getCumulativeReturnFeign(5,
                LocalDate.now().minusMonths(6), LocalDate.now(), new BigDecimal(0)));

        Assertions.assertEquals("Stock Calculation Webservice is down", stockException.getMessage());

    }


    public StockFundamentalsVO createStockFundamentalsVO(String tickerSymbol, BigDecimal cumulativeReturn, BigDecimal marketCap) {
        StockFundamentalsVO stockFundamentalsVO = new StockFundamentalsVO();
        stockFundamentalsVO.setTickerName(tickerSymbol);
        stockFundamentalsVO.setTickerName(tickerSymbol);
        stockFundamentalsVO.setCumulativeReturn(cumulativeReturn);
        stockFundamentalsVO.setMarketCap(marketCap);

        return stockFundamentalsVO;
    }

    public CRSResponseVO createResponseObjects(String ticker, BigDecimal cumulativeReturn) {
        CRSResponseVO crsResponseVO = new CRSResponseVO();
        crsResponseVO.setTicker(ticker);
        crsResponseVO.setCumulativeReturn(cumulativeReturn);

        return crsResponseVO;
    }
}
