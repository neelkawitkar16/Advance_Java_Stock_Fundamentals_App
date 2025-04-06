package org.eureka.stockAnalytics.entity.stocks;

import jakarta.persistence.*;
import org.eureka.stockAnalytics.vo.TopStockBySectorVO;

import java.math.BigDecimal;
import java.util.Objects;

@Entity
@Table(name = "stock_fundamentals", schema = "endeavour")

@NamedNativeQuery(
        name = "StocksFundamentals.TopStockBySector",
        query = """
        with intital as (select
            sf.ticker_symbol,
            sl.ticker_name,
            sl2.sector_id,
            sl2.sector_name,
            sf.market_cap,
            rank() over (partition by sf.sector_id order by sf.market_cap desc) as market_cap_Rank
        from
            endeavour.stock_fundamentals sf,
            endeavour.stocks_lookup sl ,
            endeavour.sector_lookup sl2
        where
            sf.sector_id = sl2.sector_id and
            sf.ticker_symbol = sl.ticker_symbol)
        select
            ini.ticker_symbol,
            ini.ticker_name,
            ini.sector_id,
            ini.sector_name,
            ini.market_cap
        from
            intital ini
        where
            market_cap_Rank = 1;
        """,
        resultSetMapping = "StocksFundamentals.TopStockBySectorMapping")

@NamedNativeQuery(
        name = "StocksFundamentals.Top3StocksBySector",
        query = """
        with intital as (select
            sf.ticker_symbol,
            sl.ticker_name,
            sl2.sector_id,
            sl2.sector_name,
            sf.market_cap,
            rank() over (partition by sf.sector_id order by sf.market_cap desc) as market_cap_Rank
        from
            endeavour.stock_fundamentals sf,
            endeavour.stocks_lookup sl ,
            endeavour.sector_lookup sl2
        where
            sf.sector_id = sl2.sector_id and
            sf.ticker_symbol = sl.ticker_symbol)
        select
            ini.ticker_symbol,
            ini.ticker_name,
            ini.sector_id,
            ini.sector_name,
            ini.market_cap
        from
            intital ini
        where
            market_cap_Rank in (1,2,3);
        """,
        resultSetMapping = "StocksFundamentals.TopStockBySectorMapping")

@SqlResultSetMapping(
        name = "StocksFundamentals.TopStockBySectorMapping",
        classes = @ConstructorResult(targetClass = TopStockBySectorVO.class,
        columns = {
                    @ColumnResult(name = "ticker_symbol", type = String.class),
                    @ColumnResult(name = "ticker_name", type = String.class),
                    @ColumnResult(name = "sector_id", type = Integer.class),
                    @ColumnResult(name = "sector_name", type = String.class),
                    @ColumnResult(name = "market_cap", type = BigDecimal.class),
}))
public class StocksFundamentals {
    @Column(name = "ticker_symbol")
    @Id
    private String tickerSymbol;

/*    @Column(name = "sector_id")
    private int sectorID;

    @Column(name = "subsector_id")
    private int subSectorID;*/

    @Column(name = "market_cap")
    private BigDecimal marketCap;
    @Column(name = "current_ratio")
    private  BigDecimal currentRatio;

    @OneToOne
    @JoinColumn(name = "ticker_symbol", referencedColumnName = "ticker_symbol")
    private CompanyLocations location;

    // Many-to-One with SectorLookup
    @ManyToOne
    @JoinColumn(name = "sector_id")
    private SectorLookup sectorLookup;

    // Many-to-One with SubSectorLookup
    @ManyToOne
    @JoinColumn(name = "subsector_id")
    private SubSectorLookup subSectorName;

    public SectorLookup getSectorLookup() {
        return sectorLookup;
    }

    public void setSectorLookup(SectorLookup sectorLookup) {
        this.sectorLookup = sectorLookup;
    }

    public SubSectorLookup getSubSectorName() {
        return subSectorName;
    }

    public void setSubSectorName(SubSectorLookup subSectorName) {
        this.subSectorName = subSectorName;
    }

    @Override
    public String toString() {
        return "StocksFundamentals{" +
                "tickerSymbol='" + tickerSymbol + '\'' +
//                ", sectorID=" + sectorID +
//                ", subSectorID=" + subSectorID +
                ", marketCap=" + marketCap +
                ", currentRatio=" + currentRatio +
                "}\n";
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof StocksFundamentals that)) return false;
        return Objects.equals(getTickerSymbol(), that.getTickerSymbol());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getTickerSymbol());
    }

    public String getTickerSymbol() {
        return tickerSymbol;
    }

    public void setTickerSymbol(String tickerSymbol) {
        this.tickerSymbol = tickerSymbol;
    }

/*    public int getSectorID() {
        return sectorID;
    }

    public void setSectorID(int sectorID) {
        this.sectorID = sectorID;
    }

    public int getSubSectorID() {
        return subSectorID;
    }

    public void setSubSectorID(int subSectorID) {
        this.subSectorID = subSectorID;
    }*/

    public BigDecimal getMarketCap() {
        return marketCap;
    }

    public void setMarketCap(BigDecimal marketCap) {
        this.marketCap = marketCap;
    }

    public BigDecimal getCurrentRatio() {
        return currentRatio;
    }

    public void setCurrentRatio(BigDecimal currentRatio) {
        this.currentRatio = currentRatio;
    }
}
