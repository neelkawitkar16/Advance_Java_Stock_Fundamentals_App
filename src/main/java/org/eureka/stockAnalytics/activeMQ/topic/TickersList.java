package org.eureka.stockAnalytics.activeMQ.topic;

import java.io.Serializable;
import java.util.List;

public class TickersList implements Serializable {
    private List<String> tickers;

    public List<String> getTickers() {
        return tickers;
    }

    public void setTickers(List<String> tickers) {
        this.tickers = tickers;
    }
}
