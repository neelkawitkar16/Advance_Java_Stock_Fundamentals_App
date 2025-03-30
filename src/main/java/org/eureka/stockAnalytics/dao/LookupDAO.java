package org.eureka.stockAnalytics.dao;

import org.eureka.stockAnalytics.rowMappers.SectorRowMapper;

import org.eureka.stockAnalytics.vo.SectorVO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class LookupDAO {
    @Autowired
    JdbcTemplate jdbcTemplate;

    public List<SectorVO> getAllSectors() {
        String sqlQuery = """
                SELECT 
                    * 
                FROM 
                    endeavour.sector_lookup
                """;
        return jdbcTemplate.query(sqlQuery, new SectorRowMapper());
    }

    public SectorVO getSectorById(int sectorId) {
        String sqlQuery = """
                SELECT 
                    * 
                FROM 
                    endeavour.sector_lookup
                WHERE
                    sector_id = ?
                """;

        Object[] inputParams = new Object[]{sectorId};
        return jdbcTemplate.queryForObject(sqlQuery, inputParams, new SectorRowMapper());
    }
}
