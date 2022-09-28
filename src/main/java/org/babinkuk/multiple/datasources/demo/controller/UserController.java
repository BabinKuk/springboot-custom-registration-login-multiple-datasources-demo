package org.babinkuk.multiple.datasources.demo.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

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
@RequestMapping("/users")
public class UserController {
	
	private UserService userService;
	
	@Autowired
	public UserController(UserService userService) {
		this.userService = userService;
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
		
		return "users/user-form";
	}
	
	// expose GET "/users/showFormForUpdate"
	@GetMapping("/showFormForUpdate")
	public String showFormForUpdate(@RequestParam("userId") int id, Model theModel) {
		
		// get user
		Optional<User> existingUser = userService.findById(id);
		
		User user = null;
		
		if (existingUser.isPresent()) {
			user = existingUser.get();
		}
		
		theModel.addAttribute("user", user);
		
		return "users/user-form";
	}
	
	// handler method to handle user registration form submit request
	@PostMapping("/save")
	public String saveUSer(@Valid @ModelAttribute("user") UserDao userDao, BindingResult result, Model model) {
		
//		User existingUser = userService.findByUsername(userDao.getUsername());
//        
//		if (existingUser != null && existingUser.getUsername() != null && !existingUser.getUsername().isEmpty()) {
//        	result.rejectValue("username", null, "There is already an account registered with the same username");
//        }
//        
//        if (result.hasErrors()) {
//			model.addAttribute("user", userDao);
//			return "/showFormForUpdate";
//        }
        
		userService.saveUser(userDao);
		
		return "redirect:/users?success";
    }
	
	// expose GET "/users/delete"
	@GetMapping("/delete")
	public String deleteUser(@RequestParam("userId") int id) {
		
		// delete user
		userService.delete(id);
		
		// redirect to prevent duplicate submissions
		return "redirect:/users";
	}

}
