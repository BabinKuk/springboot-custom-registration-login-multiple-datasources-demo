package org.babinkuk.multiple.datasources.demo.user.repository;

import java.util.Optional;

import org.babinkuk.multiple.datasources.demo.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
	
	public User findByEmail(String email);
	
	public User findByUsername(String username);
}
