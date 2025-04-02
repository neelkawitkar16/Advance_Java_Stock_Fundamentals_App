package org.eureka.stockAnalytics.entity.stocks;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.util.Objects;

@Entity
@Table(name = "subsector_lookup", schema = "endeavour")
public class SubSectorLookup {
    @Column(name = "subsector_id")
    @Id
    private Integer subSectorID;
    @Column(name = "subsector_name")
    private String subSectorName;
    @Column(name = "sector_id")
    private Integer sectorID;

    public Integer getSubSectorID() {
        return subSectorID;
    }

    public void setSubSectorID(Integer subSectorID) {
        this.subSectorID = subSectorID;
    }

    public String getSubSectorName() {
        return subSectorName;
    }

    public void setSubSectorName(String subSectorName) {
        this.subSectorName = subSectorName;
    }

    public Integer getSectorID() {
        return sectorID;
    }

    public void setSectorID(Integer sectorID) {
        this.sectorID = sectorID;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof SubSectorLookup that)) return false;
        return Objects.equals(getSubSectorID(), that.getSubSectorID());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getSubSectorID());
    }

    @Override
    public String toString() {
        return "SubSectorLookup{" +
                "subSectorID=" + subSectorID +
                ", subSectorName='" + subSectorName + '\'' +
                ", sectorID=" + sectorID +
                "}\n";
    }
}
