package com.crud.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.crud.model.Employee;
import com.crud.service.EmployeeService;

@Controller
public class EmployeeController {

	@Autowired
	private EmployeeService employeeService;
	
	@RequestMapping(value="/", method=RequestMethod.GET)
	public ModelAndView home() {
		
		//insertEmployee();
		employeeService.deleteEmployeeById(14);
		employeeService.updateEmployeeMail("surajcse33@gmail.com", 13);
		//Employee employee = employeeService.getEmployeeById(13);
		//System.out.println(employee.getEmployeeId()+"\t"+employee.getEmployeeName()+"\t"+employee.getEmail()+"\t"+employee.getSalary()+"\t"+employee.getDeptNum());
		
		List<Employee> list = employeeService.getAllEmployees();
		for (Employee employee1 : list) {
			System.out.println(employee1.getEmployeeId()+"\t"+employee1.getEmployeeName()+"\t"+employee1.getEmail()+"\t"+employee1.getSalary()+"\t"+employee1.getDeptNum());
		}
 		
		return new ModelAndView("home", "message", "welcome to Employee controller");
	}

	private void insertEmployee() {
		Employee employee = new Employee();
		employee.setEmployeeName("Aama");
		employee.setEmail("aama23@gail.com");
		employee.setSalary(45000);
		employee.setDeptNum(10);
		
		employeeService.insertEmployee(employee);
	}
}
