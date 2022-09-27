package org.babinkuk.multiple.datasources.demo.employee.service;

import java.util.Optional;

import org.babinkuk.multiple.datasources.demo.employee.entity.Employee;
import org.babinkuk.multiple.datasources.demo.employee.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class EmployeeServiceImpl implements EmployeeService {
	
	private EmployeeRepository employeeRepository;
	
	public EmployeeServiceImpl() {
		// TODO Auto-generated constructor stub
	}
	
	@Autowired
	public EmployeeServiceImpl(EmployeeRepository employeeRepository) {
		this.employeeRepository = employeeRepository;
	}
	
	@Override
	public Employee findById(int id) {
		Optional<Employee> result = employeeRepository.findById(id);
		
		Employee employee = null;
		
		if (result.isPresent()) {
			employee = result.get();
//		} else {
//			// employee not found
//			throw new RuntimeException("Employee id " + id + " not found.");
		}
		
		return employee;
	}
	
	@Override
	public void save(Employee employee) {
		employeeRepository.save(employee);
	}
	
	@Override
	public void delete(int id) {
		employeeRepository.deleteById(id);
	}

	@Override
	public Iterable<Employee> findAll() {
		//return employeeRepository.findAll();
		return employeeRepository.findAllByOrderByLastNameAsc();
	}

}
