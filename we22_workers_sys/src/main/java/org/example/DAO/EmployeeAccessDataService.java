package org.example.DAO;

import org.example.model.Employee;
import org.example.model.EmployeeRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository("employeeMySQL")
public class EmployeeAccessDataService implements EmployeeDAO{
    private final JdbcTemplate jdbcTemplate;
    @Autowired
    public EmployeeAccessDataService(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }
    @Override
    public List<Employee> selectAllEmployee() {
        final String sql = """
                select * from employee;
                """;
        return jdbcTemplate.query(sql,new EmployeeRowMapper());
    }

    @Override
    public Optional<Employee> selectEmployeeById(String employeeId) {
        final String sql = "select * from employee where employeeId = ?;";
        List<Employee> employeeList = jdbcTemplate.query(sql, new EmployeeRowMapper(), employeeId);
        return employeeList.stream().findFirst();
    }
    @Override
    public Optional<Employee> selectEmployeeByName(String employeeName) {
        final String sql = "select * from employee where employeeName = ?;";
        List<Employee> employeeList = jdbcTemplate.query(sql, new EmployeeRowMapper(), employeeName);
        return employeeList.stream().findFirst();
    }
    @Override
    public String insertEmployee(Employee employee) {
        final String sql = "insert into employee value(?,?,?,?,?,?)";
        int result = jdbcTemplate.update(
                sql,
                employee.getEmployeeId(),
                employee.getEmployeeName(),
                employee.getGender(),
                employee.getAge(),
                employee.getBaseSalary(),
                employee.getLocationId()
        );
        return result>0?"插入成功":"插入失败";
    }

    @Override
    public String updateEmployee(String oldName,Employee employee) {
        final String sql = "update employee set employeeId=?,employeeName=?,gender=?,age=?,baseSalary=?,locationId=? where employeeName=?";
        int result = jdbcTemplate.update(
                sql,
                employee.getEmployeeId(),
                employee.getEmployeeName(),
                employee.getGender(),
                employee.getAge(),
                employee.getBaseSalary(),
                employee.getLocationId(),
                oldName
                );
        return result>0?"更新成功":"更新失败";
    }

    @Override
    public String deleteEmployeeById(String employeeId) {
        final String sql = "delete from employee where employeeId=?";
        jdbcTemplate.update(sql, employeeId);
        return "删除成功";
    }
    public String deleteEmployeeByName(String employeeName) {
        final String sql = "delete from employee where employeeName=?";
        jdbcTemplate.update(sql, employeeName);
        return "删除成功";
    }
}
