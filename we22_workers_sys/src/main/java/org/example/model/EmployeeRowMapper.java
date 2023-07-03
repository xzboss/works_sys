package org.example.model;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class EmployeeRowMapper implements RowMapper {
    @Override
    public Object mapRow(ResultSet resultSet, int i) throws SQLException {
        //ResultSet 接收数据库返回信息
        return new Employee(
                resultSet.getString("employeeId"),
                resultSet.getString("employeeName"),
                resultSet.getString("gender"),
                resultSet.getInt("age"),
                resultSet.getDouble("baseSalary"),
                resultSet.getString("locationId")
        );
    }
}
