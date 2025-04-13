package org.eureka.stockAnalytics.vo;

import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;
import java.util.Objects;

@Schema(name = "Stocks Fundamentals with Names", description = "Stock Fundamentals with Cumulative Return")
public class StockFundamentalsVO {
    @Schema(name = "Stocks Ticker", description = "Indicates each stocks ticker symbol", example = "AAPL")
    private String tickerSymbol;
    @Schema(name = "Stocks Ticker Name", description = "Indicates each stocks ticker name", example = "Apple Inc.")
    private String tickerName;
    @Schema(name = "Stocks Sector ID", description = "Indicates each stocks sector ID", example = "37")
    private Integer sectorID;
    @Schema(name = "Stocks Sector Name", description = "Indicates each stocks sector name", example = "Technology")
    private String sectorName;
    @Schema(name = "Stocks Sub-Sector ID", description = "Indicates each stocks sub-sector ID", example = "273")
    private Integer subSectorID;
    @Schema(name = "Stocks Sub-Sector Name", description = "Indicates each stocks sub-sector name", example = "Electronics & Computer Distribution")
    private String subSectorName;
    @Schema(name = "Stocks MarketCap", description = "Indicates each stocks market cap", example = "2829863354368")
    private BigDecimal marketCap;
    @Schema(name = "Stocks MarketCap", description = "Indicates each stocks market cap", example = "2829863354368")
    private BigDecimal currentRatio;
    private BigDecimal cumulativeReturn;

    public BigDecimal getCumulativeReturn() {
        return cumulativeReturn;
    }

    public void setCumulativeReturn(BigDecimal cumulativeReturn) {
        this.cumulativeReturn = cumulativeReturn;
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
                ", cumulativeReturn=" + cumulativeReturn +
                "}\n";
    }
}
