package org.eureka.stockAnalytics.dto;

import java.math.BigDecimal;

public class StateMarketCapDTO {
    private String state;
    private BigDecimal totalMarketCap;

    public StateMarketCapDTO(String state, BigDecimal totalMarketCap) {
        this.state = state;
        this.totalMarketCap = totalMarketCap;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public BigDecimal getTotalMarketCap() {
        return totalMarketCap;
    }

    public void setTotalMarketCap(BigDecimal totalMarketCap) {
        this.totalMarketCap = totalMarketCap;
    }


}
