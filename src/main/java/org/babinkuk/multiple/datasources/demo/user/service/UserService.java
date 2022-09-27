package org.babinkuk.multiple.datasources.demo.user.service;

import java.util.List;

import org.babinkuk.multiple.datasources.demo.user.dao.UserDao;
import org.babinkuk.multiple.datasources.demo.user.entity.User;

public interface UserService {

	public User findByUsername(String username);

	public void saveUser(UserDao userDao);
	
	public List<UserDao> getUsers();
}
