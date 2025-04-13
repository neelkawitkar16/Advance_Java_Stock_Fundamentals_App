package org.eureka.stockAnalytics.vo;

import java.util.List;

public class SubsectorTopStocksVO {
    private String sectorName;
    private String subsectorName;
    private List<TopStockWithReturnVO> topStocks;

    public SubsectorTopStocksVO(String sectorName, String subsectorName, List<TopStockWithReturnVO> topStocks) {
        this.sectorName = sectorName;
        this.subsectorName = subsectorName;
        this.topStocks = topStocks;
    }

    public String getSectorName() {
        return sectorName;
    }

    public void setSectorName(String sectorName) {
        this.sectorName = sectorName;
    }

    public String getSubsectorName() {
        return subsectorName;
    }

    public void setSubsectorName(String subsectorName) {
        this.subsectorName = subsectorName;
    }

    public List<TopStockWithReturnVO> getTopStocks() {
        return topStocks;
    }

    public void setTopStocks(List<TopStockWithReturnVO> topStocks) {
        this.topStocks = topStocks;
    }
}
