package com.crud.service;

import java.util.List;

import com.crud.model.Employee;

public interface EmployeeService {
	
	public void insertEmployee(Employee employee);
	public Employee getEmployeeById(int employeeId);
	public List<Employee> getAllEmployees();
	public void deleteEmployeeById(int employeeId);
	public void updateEmployeeMail(String email, int employeeId);

}
