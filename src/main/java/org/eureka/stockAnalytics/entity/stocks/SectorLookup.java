package org.eureka.stockAnalytics.entity.stocks;

import jakarta.persistence.*;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Entity
@Table(name = "sector_lookup", schema = "endeavour")
public class SectorLookup {
    @Column(name = "sector_id")
    @Id
    private Integer sectorID;
    @Column(name = "sector_name")
    private String sectorName;

    /*// One-to-Many with StocksFundamentals
    @OneToMany(mappedBy = "sectorLookup", fetch = FetchType.EAGER)
    private List<StocksFundamentals> stocksFundamentalsList;

    // One-to-Many with SubSectorLookup
    @OneToMany(mappedBy = "sectorLookup", fetch = FetchType.EAGER)
    private List<SubSectorLookup> subSectorLookupList;*/

    @OneToMany(mappedBy = "sectorID",fetch = FetchType.EAGER)
    private List<SubSectorLookup> subSectorLookup;


    public Map<Integer, String> getSubSectorLookup() {
        return subSectorLookup.stream()
                .collect(Collectors.toMap(SubSectorLookup::getSubSectorID, SubSectorLookup::getSubSectorName));
    }

    public void setSubSectorLookup(List<SubSectorLookup> subSectorLookup) {
        this.subSectorLookup = subSectorLookup;
    }


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
