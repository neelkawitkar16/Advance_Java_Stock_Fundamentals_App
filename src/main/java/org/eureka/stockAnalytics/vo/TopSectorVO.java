package org.eureka.stockAnalytics.vo;

import java.util.List;

public class TopSectorVO {
    private Integer sectorID;
    private String sectorName;
    private List<TopStockVO> topStocks;

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

    public List<TopStockVO> getTopStocks() {
        return topStocks;
    }

    public void setTopStocks(List<TopStockVO> topStocks) {
        this.topStocks = topStocks;
    }
}
