package org.babinkuk.multiple.datasources.demo.password.dao;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.babinkuk.multiple.datasources.demo.validation.FieldMatch;

@FieldMatch.List({
	@FieldMatch(first = "newPassword", second = "matchingPassword", message = "The password fields must match")
})
public class PasswordDao {

	private String token;
	
	@NotNull(message = "is required")
	@Size(min = 4, message = "is invalid")
	private String newPassword;
	
	@NotNull(message = "is required")
	@Size(min = 4, message = "is invalid")
	private String matchingPassword;

	public PasswordDao() {
	}
	
	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

	public String getMatchingPassword() {
		return matchingPassword;
	}

	public void setMatchingPassword(String matchingPassword) {
		this.matchingPassword = matchingPassword;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	@Override
	public String toString() {
		return "PasswordDao [token=" + token + ", newPassword=" + "******" + ", matchingPassword=" + matchingPassword + "]";
	}
}
