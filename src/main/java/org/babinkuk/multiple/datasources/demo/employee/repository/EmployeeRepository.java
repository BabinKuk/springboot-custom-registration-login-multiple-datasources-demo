package org.babinkuk.multiple.datasources.demo.employee.repository;

import java.util.List;

import org.babinkuk.multiple.datasources.demo.employee.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee, Integer>{

	// that's it... no need to write any code
	// optional
	public Employee findByEmail(String email);
	
	// custom method to sort by lastName
	public List<Employee> findAllByOrderByLastNameAsc();
	
	// search by name
	public List<Employee> findByFirstNameContainsOrLastNameContainsAllIgnoreCase(String name, String lName);

}
 