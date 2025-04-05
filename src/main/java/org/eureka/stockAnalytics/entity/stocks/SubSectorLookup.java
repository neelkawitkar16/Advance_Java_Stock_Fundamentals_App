package org.eureka.stockAnalytics.entity.stocks;

import jakarta.persistence.*;

import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "subsector_lookup", schema = "endeavour")
public class SubSectorLookup {
    @Column(name = "subsector_id")
    @Id
    private Integer subSectorID;
    @Column(name = "subsector_name")
    private String subSectorName;
   /* @Column(name = "sector_id")
    private Integer sectorID;*/

    // Many-to-One with SectorLookup
    @ManyToOne
    @JoinColumn(name = "sector_id")
    private SectorLookup sectorID;

    public SectorLookup getSectorID() {
        return sectorID;
    }

    public void setSectorID(SectorLookup sectorID) {
        this.sectorID = sectorID;
    }
/*    // One-to-Many with StocksFundamentals
    @OneToMany(mappedBy = "subSectorLookup")
    private List<StocksFundamentals> stocksFundamentalsList;*/

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

/*    public Integer getSectorID() {
        return sectorID;
    }

    public void setSectorID(Integer sectorID) {
        this.sectorID = sectorID;
    }*/

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
