package org.eureka.stockAnalytics.vo;

import java.util.Objects;

public class SampleRequest {
    private String tickerSymbol;
    private String tickerName;
    private String state;

    @Override
    public String toString() {
        return "SampleRequest{" +
                "tickerSymbol='" + tickerSymbol + '\'' +
                ", tickerName='" + tickerName + '\'' +
                ", State='" + state + '\'' +
                "}\n";
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof SampleRequest that)) return false;
        return Objects.equals(getTickerSymbol(), that.getTickerSymbol()) && Objects.equals(getTickerName(), that.getTickerName()) && Objects.equals(getState(), that.getState());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getTickerSymbol(), getTickerName(), getState());
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

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
