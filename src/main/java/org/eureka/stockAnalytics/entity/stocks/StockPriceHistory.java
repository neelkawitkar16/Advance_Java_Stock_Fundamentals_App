package org.eureka.stockAnalytics.entity.stocks;

import jakarta.persistence.*;
import org.eureka.stockAnalytics.vo.StockPriceHistoryKey;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

@Entity
@Table(name = "stocks_price_history", schema = "endeavour")
@IdClass(value = StockPriceHistoryKey.class)
public class StockPriceHistory {
    @Column(name = "ticker_symbol")
    @Id
    private String tickerSymbol;
    @Column(name = "trading_date")
    @Id
    private LocalDate tradingDate;
    @Column(name = "open_price")
    private BigDecimal openPrice;
    @Column(name = "close_price")
    private BigDecimal closePrice;
    @Column(name = "low_price")
    private BigDecimal lowPrice;
    @Column(name = "high_price")
    private BigDecimal highPrice;
    @Column(name = "volume")
    private BigDecimal volume;

    @Override
    public String toString() {
        return "StockPriceHistory{" +
                "tickerSymbol='" + tickerSymbol + '\'' +
                ", tradingDate=" + tradingDate +
                ", openPrice=" + openPrice +
                ", closePrice=" + closePrice +
                ", lowPrice=" + lowPrice +
                ", highPrice=" + highPrice +
                ", volume=" + volume +
                "}\n";
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof StockPriceHistory that)) return false;
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

    public BigDecimal getOpenPrice() {
        return openPrice;
    }

    public void setOpenPrice(BigDecimal openPrice) {
        this.openPrice = openPrice;
    }

    public BigDecimal getClosePrice() {
        return closePrice;
    }

    public void setClosePrice(BigDecimal closePrice) {
        this.closePrice = closePrice;
    }

    public BigDecimal getLowPrice() {
        return lowPrice;
    }

    public void setLowPrice(BigDecimal lowPrice) {
        this.lowPrice = lowPrice;
    }

    public BigDecimal getHighPrice() {
        return highPrice;
    }

    public void setHighPrice(BigDecimal highPrice) {
        this.highPrice = highPrice;
    }

    public BigDecimal getVolume() {
        return volume;
    }

    public void setVolume(BigDecimal volume) {
        this.volume = volume;
    }
}
