package org.eureka.stockAnalytics.remote;

import org.eureka.stockAnalytics.config.FeignConfig;
import org.eureka.stockAnalytics.vo.CRSRequestVO;
import org.eureka.stockAnalytics.vo.CRSResponseVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.LocalDate;
import java.util.List;

@FeignClient(name = "StockCalculationClient", configuration = {FeignConfig.class}, url = "${client.stock-calculations.url}")
public interface StockCalculationClient {

    @PostMapping(value = "/calculate/cumulative-return/{fromDate}/{toDate}")
    public List<CRSResponseVO> getCumulativeReturn(@PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate fromDate,
                                                   @PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate toDate,
                                                   @RequestBody CRSRequestVO crsRequestVO);
}
