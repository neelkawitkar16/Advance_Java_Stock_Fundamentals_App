package org.eureka.stockAnalytics.dto;

import org.eureka.stockAnalytics.vo.StockPriceHistoryVO;

import java.math.BigDecimal;
import java.util.List;

public class StockFundamentalsWithPriceHistoryDTO {
    private BigDecimal marketCap;
    private BigDecimal currentRatio;
    private List<StockPriceHistoryVO> tradingHistoryList;

    public StockFundamentalsWithPriceHistoryDTO(BigDecimal marketCap, BigDecimal currentRatio, List<StockPriceHistoryVO> tradingHistoryList) {
        this.marketCap = marketCap;
        this.currentRatio = currentRatio;
        this.tradingHistoryList = tradingHistoryList;
    }

    public StockFundamentalsWithPriceHistoryDTO() {}

    public BigDecimal getMarketCap() {
        return marketCap;
    }

    public void setMarketCap(BigDecimal marketCap) {
        this.marketCap = marketCap;
    }

    public BigDecimal getCurrentRatio() {
        return currentRatio;
    }

    public void setCurrentRatio(BigDecimal currentRatio) {
        this.currentRatio = currentRatio;
    }

    public List<StockPriceHistoryVO> getTradingHistoryList() {
        return tradingHistoryList;
    }

    public void setTradingHistoryList(List<StockPriceHistoryVO> tradingHistoryList) {
        this.tradingHistoryList = tradingHistoryList;
    }
}
