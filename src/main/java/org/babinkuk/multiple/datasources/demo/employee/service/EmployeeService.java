package org.babinkuk.multiple.datasources.demo.employee.service;

import org.babinkuk.multiple.datasources.demo.employee.entity.Employee;

public interface EmployeeService {
	
	public Iterable<Employee> findAll();
	
	public Employee findById(int id);
	
	public void save(Employee employee);
	
	public void delete(int id);
	
}
