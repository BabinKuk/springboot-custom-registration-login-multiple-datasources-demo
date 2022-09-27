package org.babinkuk.multiple.datasources.demo.user.repository;

import org.babinkuk.multiple.datasources.demo.user.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Integer> {
	
	public Role findByName(String name);
}
