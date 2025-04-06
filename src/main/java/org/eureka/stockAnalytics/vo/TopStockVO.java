package org.eureka.stockAnalytics.vo;

import java.math.BigDecimal;

public class TopStockVO {
    private String tickerSymbol;
    private String tickerName;
    private BigDecimal marketCap;

    public String getTickerSymbol() {
        return tickerSymbol;
    }

    public void setTickerSymbol(String tickerSymbol) {
        this.tickerSymbol = tickerSymbol;
    }

    public String getTickerName() {
        return tickerName;
    }

    public void setTickerName(String tickerName) {
        this.tickerName = tickerName;
    }

    public BigDecimal getMarketCap() {
        return marketCap;
    }

    public void setMarketCap(BigDecimal marketCap) {
        this.marketCap = marketCap;
    }
}
