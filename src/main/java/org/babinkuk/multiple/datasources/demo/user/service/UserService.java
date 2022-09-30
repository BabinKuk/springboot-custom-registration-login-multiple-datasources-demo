package org.babinkuk.multiple.datasources.demo.user.service;

import java.util.List;
import java.util.Optional;

import org.babinkuk.multiple.datasources.demo.user.dao.UserDao;
import org.babinkuk.multiple.datasources.demo.user.entity.User;

public interface UserService {
	
	public UserDao findByUsername(String username);

	public void saveUser(UserDao userDao);
	
	public List<UserDao> getUsers();

	public UserDao findById(Integer id);
	
	public void delete(int id);

}
