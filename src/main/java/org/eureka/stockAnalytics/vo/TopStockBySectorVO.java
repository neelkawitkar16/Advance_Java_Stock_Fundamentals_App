package org.eureka.stockAnalytics.vo;

import java.math.BigDecimal;

public class TopStockBySectorVO {
    private Integer sectorID;
    private String sectorName;
    private String tickerSymbol;
    private String tickerName;
    private BigDecimal marketCap;

    public TopStockBySectorVO(String tickerSymbol,String tickerName, Integer sectorID, String sectorName, BigDecimal marketCap) {
        this.sectorID = sectorID;
        this.sectorName = sectorName;
        this.tickerSymbol = tickerSymbol;
        this.tickerName = tickerName;
        this.marketCap = marketCap;
    }

    public Integer getSectorID() {
        return sectorID;
    }

    public void setSectorID(Integer sectorID) {
        this.sectorID = sectorID;
    }

    public String getSectorName() {
        return sectorName;
    }

    public void setSectorName(String sectorName) {
        this.sectorName = sectorName;
    }

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

    @Override
    public String toString() {
        return "TopStockBySectorVO{" +
                "sectorID=" + sectorID +
                ", sectorName='" + sectorName + '\'' +
                ", tickerSymbol='" + tickerSymbol + '\'' +
                ", tickerName='" + tickerName + '\'' +
                ", marketCap=" + marketCap +
                "}\n";
    }
}
