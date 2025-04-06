package org.eureka.stockAnalytics.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.eureka.stockAnalytics.entity.stocks.StocksFundamentals;
import org.eureka.stockAnalytics.vo.StockFundamentalsVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class StockFundamentalsDAO {

    @Autowired
    NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Autowired
    @Qualifier(value = "entityManagerFactory")
    EntityManager entityManager;

    public List<StockFundamentalsVO> getStockFundamentals(List<String> tickersList) {
        String sqlQuery = """
                SELECT 
                        sf.*,
                        ssl.subsector_name,
                        sl.sector_name,
                        sl2.ticker_name
                    FROM 
                        endeavour.stock_fundamentals sf
                    LEFT JOIN 
                        endeavour.subsector_lookup ssl
                     ON 
                        sf.subsector_id=ssl.subsector_id
                    LEFT JOIN 
                        endeavour.sector_lookup sl
                    ON 
                        sf.sector_id=sl.sector_id
                    LEFT JOIN 
                        endeavour.stocks_lookup sl2
                    ON 
                        sf.ticker_symbol=sl2.ticker_symbol
                    WHERE 
                        sf.ticker_symbol IN (:tickers)
                """;

        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("tickers", tickersList);

        List<StockFundamentalsVO> stockFundamentalsList = namedParameterJdbcTemplate.query(sqlQuery, mapSqlParameterSource, (rs, rowNum) -> {
            StockFundamentalsVO stockFundamentalsVO = new StockFundamentalsVO();
            stockFundamentalsVO.setTickerName(rs.getString("ticker_name"));
            stockFundamentalsVO.setTickerSymbol(rs.getString("ticker_symbol"));
            stockFundamentalsVO.setSectorID(rs.getInt("sector_id"));
            stockFundamentalsVO.setSectorName(rs.getString("sector_name"));
            stockFundamentalsVO.setSubSectorID(rs.getInt("subsector_id"));
            stockFundamentalsVO.setSubSectorName(rs.getString("subsector_name"));
            stockFundamentalsVO.setMarketCap(rs.getBigDecimal("market_cap"));
            stockFundamentalsVO.setCurrentRatio(rs.getBigDecimal("current_ratio"));
            return stockFundamentalsVO;
        });
        return stockFundamentalsList;
    }

    public List<StockFundamentalsVO> getStockFundamentalsBySector(List<String> tickersList) {
        final int TECHNOLOGY_SECTOR_ID = 37;
        String sqlQuery = """
                             SELECT
                                sf.ticker_symbol,
                                sf.sector_id,
                                sf.subsector_id,
                                sf.market_cap,
                                sf.current_ratio,
                                sl.sector_name,
                                ssl.subsector_name,
                                st.ticker_name
                            FROM
                                endeavour.stock_fundamentals sf
                            JOIN
                                endeavour.sector_lookup sl ON sf.sector_id = sl.sector_id
                            JOIN
                                endeavour.subsector_lookup ssl ON sf.subsector_id = ssl.subsector_id
                            JOIN
                                endeavour.stocks_lookup st ON sf.ticker_symbol = st.ticker_symbol
                            WHERE
                                sf.ticker_symbol IN (:tickers)
                                AND sf.sector_id = :sectorId
                         """;

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("tickers", tickersList);
        params.addValue("sectorId", TECHNOLOGY_SECTOR_ID);

        return namedParameterJdbcTemplate.query(sqlQuery, params, (rs, rowNum) -> {
            StockFundamentalsVO vo = new StockFundamentalsVO();
            vo.setTickerSymbol(rs.getString("ticker_symbol"));
            vo.setSectorID(rs.getInt("sector_id"));
            vo.setSubSectorID(rs.getInt("subsector_id"));
            vo.setMarketCap(rs.getBigDecimal("market_cap"));
            vo.setCurrentRatio(rs.getBigDecimal("current_ratio"));
            vo.setSectorName(rs.getString("sector_name"));
            vo.setSubSectorName(rs.getString("subsector_name"));
            vo.setTickerName(rs.getString("ticker_name"));
            return vo;
        });
    }

    public List<StocksFundamentals> getTopNStocksByMarketCapJPQL(Integer num) {
        String jpqlQuery = """
                select
                        sf
                    from
                        StocksFundamentals sf
                    order by sf.marketCap desc
                """;
        TypedQuery<StocksFundamentals> query = entityManager.createQuery(jpqlQuery, StocksFundamentals.class);
        query.setMaxResults(num); //limit to this num
        return query.getResultList(); //no need for result which is usually needed
    }

    //Criteria BuilderAPI
    public List<StocksFundamentals> getTopNStocksCriteriaAPI(Integer num) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<StocksFundamentals> query = criteriaBuilder.createQuery(StocksFundamentals.class);//create a query for this entity
        Root<StocksFundamentals> root = query.from(StocksFundamentals.class);

        query.select(root)
                .where(criteriaBuilder.isNotNull(root.get("currentRatio")))
                .orderBy(criteriaBuilder.desc(root.get("marketCap")));

        List<StocksFundamentals> resultList = entityManager.createQuery(query)
                .setMaxResults(num)
                .getResultList();

        return resultList;
    }

}
