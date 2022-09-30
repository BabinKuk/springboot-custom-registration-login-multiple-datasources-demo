package org.babinkuk.multiple.datasources.demo.user.dao;

import java.util.Collection;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.babinkuk.multiple.datasources.demo.user.entity.Role;
import org.babinkuk.multiple.datasources.demo.validation.FieldMatch;

@FieldMatch.List({
	@FieldMatch(first = "password", second = "matchingPassword", message = "The password fields must match")
})
public class UserDao {

	private int id;
	
	@NotNull(message = "is required")
	@Size(min = 4, message = "is invalid")
	private String username;

	@NotNull(message = "is required")
	@Size(min = 4, message = "is invalid")
	private String password;
	
	@NotNull(message = "is required")
	@Size(min = 4, message = "is invalid")
	private String matchingPassword;

	@NotNull(message = "is required")
	@Size(min = 4, message = "is invalid")
	private String firstName;

	@NotNull(message = "is required")
	@Size(min = 4, message = "is invalid")
	private String lastName;

	@Email
	@NotNull(message = "is required")
	@Size(min = 4, message = "is invalid")
	private String email;
	
	private Collection<Role> roles;
		
	public UserDao() {

	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getMatchingPassword() {
		return matchingPassword;
	}

	public void setMatchingPassword(String matchingPassword) {
		this.matchingPassword = matchingPassword;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Collection<Role> getRoles() {
		return roles;
	}

	public void setRoles(Collection<Role> roles) {
		this.roles = roles;
	}

	@Override
	public String toString() {
		return "UserDao [id=" + id + ", username=" + username + ", password=" + "******" + ", firstName=" + firstName + ", lastName="
				+ lastName + ", email=" + email + ", roles=" + roles + "]";
	}
}
