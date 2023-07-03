package org.example.DAO;

import org.example.model.Employee;

import java.util.List;
import java.util.Optional;

public interface EmployeeDAO {
    List<Employee> selectAllEmployee();


    Optional<Employee> selectEmployeeById(String employeeId);
    Optional<Employee> selectEmployeeByName(String employeeName);

    String insertEmployee(Employee employee);

    String updateEmployee(String oldName, Employee employee);

    String deleteEmployeeById(String employeeId);
    String deleteEmployeeByName(String employeeName);
}
