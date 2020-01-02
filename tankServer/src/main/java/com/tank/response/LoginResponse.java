package com.tank.response;

import java.io.Serializable;

import com.tank.response.LoginResponse.LoginState;

public class LoginResponse implements Serializable {
	private String id;
	private String password;

	public static enum LoginState {
		LoginSuccess, AccountNotExist, PasswordError,ReplicatedLogin;
	}

	private LoginState loginState;

	public LoginState getLoginState() {
		return loginState;
	}

	public void setLoginState(LoginState loginState) {
		this.loginState = loginState;
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
