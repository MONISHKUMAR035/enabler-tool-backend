package com.Employeestatus.Tool.Service;

import com.Employeestatus.Tool.Model.Employee;
import java.util.List;

public interface EmployeeService {
    Employee saveEmployee(Employee employee);
    Employee addEmployee(Employee employee);
    void deleteEmployee(Long id);
    List<Employee> getAllEmployees();
    List<Employee> filterEmployees(String country, String state, String department, String status);
    Employee updateEmployee(Long id, Employee updatedEmployee);
    Employee getEmployeeById(Long id);
}
