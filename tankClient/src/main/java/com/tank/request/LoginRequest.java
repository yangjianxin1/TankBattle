package com.tank.request;

import java.io.Serializable;

/**
 * 登陆请求
 * 
 * @author Administrator
 *
 */
public class LoginRequest implements Serializable {
	private String id;
	private String password;

	public LoginRequest(String id, String password) {
		this.id = id;
		this.password = password;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
