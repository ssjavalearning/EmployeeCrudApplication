package com.crud.dao;

import java.util.List;

import com.crud.model.Employee;

public interface EmployeeDao {
	
	public void insertEmployee(Employee employee);
	public Employee getEmployeeById(int employeeId);
	public List<Employee> getAllEmployees();
	public void deleteEmployeeById(int employeeId);
	public void updateEmployeeMail(String email, int employeeId);

}
