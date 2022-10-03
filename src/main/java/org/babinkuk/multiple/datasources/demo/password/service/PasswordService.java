package org.babinkuk.multiple.datasources.demo.password.service;

import java.util.List;
import java.util.Optional;

import org.babinkuk.multiple.datasources.demo.user.dao.UserDao;
import org.babinkuk.multiple.datasources.demo.user.entity.User;

public interface PasswordService {
	
	public void updateResetPasswordToken(String token, String email) throws Exception;
	
	public User getByResetPasswordToken(String token);
	
	public void updatePassword(User user, String newPassword);
}
