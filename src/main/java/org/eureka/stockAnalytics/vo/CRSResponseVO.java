package org.eureka.stockAnalytics.vo;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;

public class CRSResponseVO {
    @JsonProperty("tickerSymbol")
    private String ticker;
    private BigDecimal cumulativeReturn;

    public String getTicker() {
        return ticker;
    }

    public void setTicker(String ticker) {
        this.ticker = ticker;
    }

    public BigDecimal getCumulativeReturn() {
        return cumulativeReturn;
    }

    public void setCumulativeReturn(BigDecimal cumulativeReturn) {
        this.cumulativeReturn = cumulativeReturn;
    }
}
