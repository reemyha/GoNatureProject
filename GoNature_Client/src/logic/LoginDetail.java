package logic;

import java.io.Serializable;
/**
 * Represents login details including ID, username, and password.
 */
public class LoginDetail implements Serializable {
	
	

	private String id,username,password;

	public LoginDetail(String username, String password) {
		this.username = username;
		this.password = password;
	}

	public LoginDetail(String id) {
		this.id = id;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
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
	
	@Override
	public String toString() {
		return "LoginDetail [id=" + id + ", username=" + username + ", password=" + password + "]";
	}
	
	

}
