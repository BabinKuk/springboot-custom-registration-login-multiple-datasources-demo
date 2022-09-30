package org.babinkuk.multiple.datasources.demo.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.babinkuk.multiple.datasources.demo.user.dao.UserDao;
import org.babinkuk.multiple.datasources.demo.user.entity.Role;
import org.babinkuk.multiple.datasources.demo.user.entity.User;
import org.babinkuk.multiple.datasources.demo.user.repository.RoleRepository;
import org.babinkuk.multiple.datasources.demo.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/users")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private RoleRepository roleRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	public UserController(UserService userService, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
		this.userService = userService;
		this.roleRepository = roleRepository;
		this.passwordEncoder = passwordEncoder;
	}
	
	// handler method to handle list of users
	@GetMapping({"/", ""})
	public String users(Model model) {
		
		List<UserDao> users = userService.getUsers();
		
		System.out.println(users.toString());
		model.addAttribute("users", users);
		
		return "users/list-users";
	}
	
	// expose GET "/users/showFormForAdd"
	@GetMapping("/showFormForAdd")
	public String showFormForAdd(Model theModel) {
		
		// create model
		UserDao user = new UserDao();
		
		theModel.addAttribute("user", user);
		
		List<Role> roles = (List<Role>) roleRepository.findAll();
		
		theModel.addAttribute("allRoles", roles);
		
		return "users/user-form";
	}
	
	// expose GET "/users/showFormForUpdate"
	@GetMapping("/showFormForUpdate")
	public String showFormForUpdate(@RequestParam(required = false) Integer id, @RequestParam(required = false) String username, Model theModel) {
		
//		System.out.println("paramters : " + id + "; username " + username);
		UserDao user = null;
		
		// get user
		if (id != null) {
			user = userService.findById(id);
		} else {
			user = userService.findByUsername(username);
		}
				
		System.out.println("get : " + user.getPassword());
		
		theModel.addAttribute("user", user);
		
		List<Role> roles = (List<Role>) roleRepository.findAll();
		
		theModel.addAttribute("allRoles", roles);
		
		return "users/user-form";
	}
	
	// handler method to handle user data form submit request
	@PostMapping("/save")
	public String saveUser(@Valid @ModelAttribute("user") UserDao userDao, BindingResult result, Model model) {
		
		if (result.hasErrors()) {
			System.err.println("has errors " + userDao.toString());
			model.addAttribute("user", userDao);
			List<Role> roles = (List<Role>) roleRepository.findAll();
			model.addAttribute("allRoles", roles);
			return "/users/user-form";
        }
        
//		// get user
//		UserDao existingUser = userService.findById(userDao.getId());
//		
//		System.out.println(userDao.getPassword() + " , id : " + userDao.getId());
//		System.out.println(existingUser.getPassword() + " , id : " + existingUser.getId());
//		
//		boolean passwordMatch = passwordEncoder.matches(userDao.getPassword(), existingUser.getPassword());
//        System.out.println("passwordMatch : " + passwordMatch);
//        
        userService.saveUser(userDao);
		
		return "redirect:/users?success";
    }
	
	// expose GET "/users/delete"
	@GetMapping("/delete")
	public String deleteUser(@RequestParam Integer id) {
		
		// delete user
		userService.delete(id);
		
		// redirect to prevent duplicate submissions
		return "redirect:/users";
	}

}
