package org.eureka.stockAnalytics.entity.stocks;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.util.Objects;

@Entity
@Table(name = "sector_lookup", schema = "endeavour")
public class SectorLookup {
    @Column(name = "sector_id")
    @Id
    private Integer sectorID;
    @Column(name = "sector_name")
    private String sectorName;

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

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof SectorLookup that)) return false;
        return getSectorID() == that.getSectorID();
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getSectorID());
    }

    @Override
    public String toString() {
        return "SectorLookup{" +
                "sectorID=" + sectorID +
                ", sectorName='" + sectorName + '\'' +
                "}\n";
    }
}
