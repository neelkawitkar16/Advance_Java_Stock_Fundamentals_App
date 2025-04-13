package org.eureka.stockAnalytics.vo;

import java.math.BigDecimal;

public class TopStockWithReturnVO extends TopStockVO{
    private BigDecimal cumulativeReturn;

    public TopStockWithReturnVO() {}

    public TopStockWithReturnVO(String tickerSymbol, String tickerName,
                                BigDecimal marketCap, BigDecimal cumulativeReturn) {
        super.setTickerSymbol(tickerSymbol);
        super.setTickerName(tickerName);
        super.setMarketCap(marketCap);
        this.cumulativeReturn = cumulativeReturn;
    }

    public BigDecimal getCumulativeReturn() {
        return cumulativeReturn;
    }

    public void setCumulativeReturn(BigDecimal cumulativeReturn) {
        this.cumulativeReturn = cumulativeReturn;
    }
}
