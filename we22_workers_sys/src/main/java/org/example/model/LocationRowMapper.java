package org.example.model;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class LocationRowMapper implements RowMapper {
    @Override
    public Object mapRow(ResultSet resultSet, int i) throws SQLException {
        //ResultSet 接收数据库返回信息
        return new Location(
                resultSet.getString("locationId"),
                resultSet.getString("locationName"),
                resultSet.getDouble("latitude"),
                resultSet.getDouble("longitude")
        );
    }
}
