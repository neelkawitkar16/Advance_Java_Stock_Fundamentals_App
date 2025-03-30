package org.eureka.stockAnalytics.vo;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

public class StockPriceHistoryVO {
    private String tickerSymbol;
    private LocalDate tradingDate;
    private BigDecimal closePrice;
    private BigDecimal openPrice;
    private BigDecimal highPrice;
    private BigDecimal lowPrice;
    private BigDecimal volume;

    @Override
    public String toString() {
        return "StockPriceHistoryVO{" +
                "tickerSymbol='" + tickerSymbol + '\'' +
                ", tradingDate=" + tradingDate +
                ", closePrice=" + closePrice +
                ", openPrice=" + openPrice +
                ", highPrice=" + highPrice +
                ", lowPrice=" + lowPrice +
                ", volume=" + volume +
                "}\n";
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof StockPriceHistoryVO that)) return false;
        return Objects.equals(getTickerSymbol(), that.getTickerSymbol()) && Objects.equals(getTradingDate(), that.getTradingDate());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getTickerSymbol(), getTradingDate());
    }

    public String getTickerSymbol() {
        return tickerSymbol;
    }

    public void setTickerSymbol(String tickerSymbol) {
        this.tickerSymbol = tickerSymbol;
    }

    public LocalDate getTradingDate() {
        return tradingDate;
    }

    public void setTradingDate(LocalDate tradingDate) {
        this.tradingDate = tradingDate;
    }

    public BigDecimal getClosePrice() {
        return closePrice;
    }

    public void setClosePrice(BigDecimal closePrice) {
        this.closePrice = closePrice;
    }

    public BigDecimal getOpenPrice() {
        return openPrice;
    }

    public void setOpenPrice(BigDecimal openPrice) {
        this.openPrice = openPrice;
    }

    public BigDecimal getHighPrice() {
        return highPrice;
    }

    public void setHighPrice(BigDecimal highPrice) {
        this.highPrice = highPrice;
    }

    public BigDecimal getLowPrice() {
        return lowPrice;
    }

    public void setLowPrice(BigDecimal lowPrice) {
        this.lowPrice = lowPrice;
    }

    public BigDecimal getVolume() {
        return volume;
    }

    public void setVolume(BigDecimal volume) {
        this.volume = volume;
    }
}
