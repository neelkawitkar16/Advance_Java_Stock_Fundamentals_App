package org.eureka.stockAnalytics.vo;

import java.math.BigDecimal;
import java.util.Objects;

public class StockFundamentalsVO {
    private String tickerSymbol;
    private String tickerName;
    private Integer sectorID;
    private String sectorName;
    private Integer subSectorID;
    private String subSectorName;
    private BigDecimal marketCap;
    private BigDecimal currentRatio;

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

    public Integer getSubSectorID() {
        return subSectorID;
    }

    public void setSubSectorID(Integer subSectorID) {
        this.subSectorID = subSectorID;
    }

    public String getSubSectorName() {
        return subSectorName;
    }

    public void setSubSectorName(String subSectorName) {
        this.subSectorName = subSectorName;
    }

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

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof StockFundamentalsVO that)) return false;
        return Objects.equals(getTickerSymbol(), that.getTickerSymbol());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getTickerSymbol());
    }

    @Override
    public String toString() {
        return "StockFundamentalsVO{" +
                "tickerSymbol='" + tickerSymbol + '\'' +
                ", tickerName='" + tickerName + '\'' +
                ", sectorID=" + sectorID +
                ", sectorName='" + sectorName + '\'' +
                ", subSectorID=" + subSectorID +
                ", subSectorName='" + subSectorName + '\'' +
                ", marketCap=" + marketCap +
                ", currentRatio=" + currentRatio +
                "}\n";
    }
}
