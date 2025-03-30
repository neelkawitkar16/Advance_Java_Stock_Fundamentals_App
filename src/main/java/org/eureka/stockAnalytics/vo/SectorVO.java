package org.eureka.stockAnalytics.vo;

import java.util.Objects;

public class SectorVO {
    private int sectorID;
    private String sectorName;

    public int getSectorID() {
        return sectorID;
    }

    public void setSectorID(int sectorID) {
        this.sectorID = sectorID;
    }

    public String getSectorName() {
        return sectorName;
    }

    public void setSectorName(String sectorName) {
        this.sectorName = sectorName;
    }

    @Override
    public String toString() {
        return "SectorVO{" +
                "sectorID=" + sectorID +
                ", sectorName='" + sectorName + '\'' +
                "}\n";
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof SectorVO sectorVO)) return false;
        return getSectorID() == sectorVO.getSectorID() && Objects.equals(getSectorName(), sectorVO.getSectorName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getSectorID(), getSectorName());
    }
}
