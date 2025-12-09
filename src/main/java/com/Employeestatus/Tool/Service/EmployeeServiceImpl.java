package com.Employeestatus.Tool.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.Employeestatus.Tool.Model.Employee;
import com.Employeestatus.Tool.Repository.EmployeeRepository;
import java.util.List;
import java.util.Optional;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Override
    public Employee saveEmployee(Employee employee) {
        return employeeRepository.save(employee);
    }

    @Override
    public Employee addEmployee(Employee employee) {
        // last_updated will be set by @PrePersist in entity
        return employeeRepository.save(employee);
    }

    @Override
    public void deleteEmployee(Long id) {
        if (employeeRepository.existsById(id)) {
            employeeRepository.deleteById(id);
        } else {
            throw new RuntimeException("Employee not found with id " + id);
        }
    }

    @Override
    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    @Override
    public List<Employee> filterEmployees(String country, String state, String department, String status) {
        return employeeRepository.findAll().stream()
                .filter(e -> (country == null || country.isEmpty() || e.getCountry().equalsIgnoreCase(country)))
                .filter(e -> (state == null || state.isEmpty() || e.getState().equalsIgnoreCase(state)))
                .filter(e -> (department == null || department.isEmpty() || e.getDepartment().equalsIgnoreCase(department)))
                .filter(e -> (status == null || status.isEmpty() || e.getStatus().equalsIgnoreCase(status)))
                .toList();
    }

    @Override
    public Employee updateEmployee(Long id, Employee updatedEmployee) {
        Optional<Employee> existing = employeeRepository.findById(id);
        if (existing.isPresent()) {
            Employee emp = existing.get();
            emp.setName(updatedEmployee.getName());
            emp.setDepartment(updatedEmployee.getDepartment());
            emp.setCountry(updatedEmployee.getCountry());
            emp.setRegion(updatedEmployee.getRegion());
            emp.setState(updatedEmployee.getState());
            emp.setStatus(updatedEmployee.getStatus());
            emp.setProject_name(updatedEmployee.getProject_name());
            emp.setEmail(updatedEmployee.getEmail());
            emp.setPhone_number(updatedEmployee.getPhone_number());
            emp.setJoining_date(updatedEmployee.getJoining_date());
            // last_updated will be set by @PreUpdate
            return employeeRepository.save(emp);
        } else {
            throw new RuntimeException("Employee not found with id " + id);
        }
    }

    @Override
    public Employee getEmployeeById(Long id) {
        return employeeRepository.findById(id).orElse(null);
    }
}
