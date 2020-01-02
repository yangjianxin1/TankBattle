package com.tank.request;

import java.io.Serializable;

/**
 * 注册请求
 * 
 * @author Administrator
 *
 */
public class RegisterRequest implements Serializable {
	private String id;
	private String password;

	public RegisterRequest(String id, String password) {
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

	@Override
	public String toString() {
		return "RegisterRequest [id=" + id + ", password=" + password + "]";
	}
	
	

}
