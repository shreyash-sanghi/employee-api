package com.employ.employ.services;

import java.util.List;

import com.employ.employ.InputSchema.Employee;

public interface EmployService {
    String createEmployee(Employee empData);
    List<Employee> readAllEmployees();
    Employee readEmployeeById(Long id);
    boolean deleteEmployee(Long id);
    String updateEmployee(Long id,Employee updatedEmployee);
} 