package org.babinkuk.multiple.datasources.demo.password.service;

import org.babinkuk.multiple.datasources.demo.user.entity.User;
import org.babinkuk.multiple.datasources.demo.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class PasswordServiceImpl implements PasswordService {

	@Autowired
	private UserRepository userRepository;
	
	public PasswordServiceImpl() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void updateResetPasswordToken(String token, String email) throws Exception {
		
		//System.out.println("updateResetPasswordToken : " + token + " ; " + email);
		
		User user = userRepository.findByEmail(email);
		System.out.println(user.toString());

		if (user != null) {
			user.setResetPasswordToken(token);
			userRepository.save(user);
		} else {
			throw new Exception("Could not find any customer with the email " + email);
		}
	}

	@Override
	public User getByResetPasswordToken(String token) {
		//System.out.println("getByResetPasswordToken " + token);
		
		return userRepository.findByResetPasswordToken(token);
	}

	@Override
	public void updatePassword(User user, String newPassword) {
		//System.out.println("updatePassword");
		PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		String encodedPassword = passwordEncoder.encode(newPassword);
		user.setPassword(encodedPassword);
		 
		user.setResetPasswordToken(null);
		userRepository.save(user);
	}
}