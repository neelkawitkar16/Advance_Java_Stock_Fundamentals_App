package org.eureka.stockAnalytics.vo;

import java.util.List;

public class CRSRequestVO {
    private List<String> tickers;

    public List<String> getTickers() {
        return tickers;
    }

    public void setTickers(List<String> tickers) {
        this.tickers = tickers;
    }
}
