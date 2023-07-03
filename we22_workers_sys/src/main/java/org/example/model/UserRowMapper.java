package org.example.model;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserRowMapper implements RowMapper {
    @Override
    public Object mapRow(ResultSet resultSet, int i) throws SQLException {
        //ResultSet 接收数据库返回信息
        return new User(
                resultSet.getString("userId"),
                resultSet.getString("userName"),
                resultSet.getString("password")
        );
    }
}
