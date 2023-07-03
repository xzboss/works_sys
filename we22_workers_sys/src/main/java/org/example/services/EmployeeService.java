package org.example.services;

import org.example.DAO.EmployeeDAO;
import org.example.model.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Component("EmployeeService")
public class EmployeeService {
    private EmployeeDAO employeeDAO;
    @Autowired
    public EmployeeService(@Qualifier("employeeMySQL") EmployeeDAO employeeDAO){
        this.employeeDAO = employeeDAO;
    }
    public List<Employee> selectAllEmployee(){
        return employeeDAO.selectAllEmployee();
    }
    public Optional<Employee> selectEmployeeById(String employeeId){
        return employeeDAO.selectEmployeeById(employeeId);
    }
    public Optional<Employee> selectEmployeeByName(String employeeName){
        return employeeDAO.selectEmployeeByName(employeeName);
    }
    public String insertEmployee(Employee employee){
        if(selectEmployeeByName(employee.getEmployeeName()).isEmpty()) return employeeDAO.insertEmployee(employee);
        else return "员工已存在";
    }
    public String updateEmployee(String oldName,Employee employee) {
        //如果不存在用户
        if(selectEmployeeByName(oldName).isEmpty()) return "员工消失了";
        else return employeeDAO.updateEmployee(oldName,employee);

    }
    public String deleteEmployeeById(String employeeId) {
        if(selectEmployeeById(employeeId).isEmpty()) return "员工消失了";
        else return employeeDAO.deleteEmployeeById(employeeId);

    }
    public String deleteEmployeeByName(String employeeName) {
        if(selectEmployeeByName(employeeName).isEmpty()) return "员工消失了";
        else return employeeDAO.deleteEmployeeByName(employeeName);

    }
}
