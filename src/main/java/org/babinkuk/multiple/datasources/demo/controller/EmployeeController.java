package org.babinkuk.multiple.datasources.demo.controller;

import java.util.List;

import javax.validation.Valid;

import org.babinkuk.multiple.datasources.demo.employee.entity.Employee;
import org.babinkuk.multiple.datasources.demo.employee.service.EmployeeService;
import org.babinkuk.multiple.datasources.demo.user.dao.UserDao;
import org.babinkuk.multiple.datasources.demo.user.entity.User;
import org.babinkuk.multiple.datasources.demo.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/employees")
public class EmployeeController {
	
	private EmployeeService employeeService;
	
	@Autowired
	public EmployeeController(EmployeeService employeeService) {
		this.employeeService = employeeService;
	}
	
	// expose GET "/employees/"
	@GetMapping({"", "/"})
	public String getAllEmployees(Model theModel) {
		
		// get employees
		Iterable<Employee> employees = employeeService.findAll();
		
		theModel.addAttribute("employees", employees);
		
		return "employees/list-employees";
	}
	
	// expose GET "/employees/showFormForAdd"
	@GetMapping("/showFormForAdd")
	public String showFormForAdd(Model theModel) {
		
		// create model
		Employee employee = new Employee();
		
		theModel.addAttribute("employee", employee);
		
		return "employees/employee-form";
	}
	
	// expose GET "/employees/showFormForUpdate"
	@GetMapping("/showFormForUpdate")
	public String showFormForUpdate(@RequestParam("employeeId") int id, Model theModel) {
		
		// get employee
		Employee employee = employeeService.findById(id);
		
		theModel.addAttribute("employee", employee);
		
		return "employees/employee-form";
	}
	
	// expose POST "/employees/save"
	@PostMapping("/save")
	public String saveEmployee(@ModelAttribute("employee") Employee employee) {
		
		// şave employee
		employeeService.save(employee);
		
		// redirect to prevent duplicate submissions
		return "redirect:/employees";
	}
	
	// expose GET "/employees/delete"
	@GetMapping("/delete")
	public String deleteEmployee(@RequestParam("employeeId") int id) {
		
		// delete employee
		employeeService.delete(id);
		
		// redirect to prevent duplicate submissions
		return "redirect:/employees";
	}

}
