package org.eureka.stockAnalytics.rowMappers;

import org.eureka.stockAnalytics.vo.SectorVO;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class SectorRowMapper implements RowMapper<SectorVO> {
    @Override
    public SectorVO mapRow(ResultSet rs, int rowNum) throws SQLException {
        SectorVO sectorVO = new SectorVO();
        sectorVO.setSectorID(rs.getInt("sector_id"));
        sectorVO.setSectorName(rs.getString("sector_name"));
        return sectorVO;
    }
}
