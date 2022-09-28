package org.babinkuk.multiple.datasources.demo.user.service;

import java.util.List;
import java.util.Optional;

import org.babinkuk.multiple.datasources.demo.user.dao.UserDao;
import org.babinkuk.multiple.datasources.demo.user.entity.User;

public interface UserService {
	
	public User findByUsername(String username);

	public void saveUser(UserDao userDao);
	
	public List<UserDao> getUsers();

	public Optional<User> findById(Integer id);
	
	public void delete(int id);
}
