package com.tank.response;

import java.io.Serializable;

public class RegisterResponse implements Serializable {
	private RegisterState registerState;

	public static enum RegisterState {
		RegisterSuccess, AccountExist;
	}

	public RegisterState getRegisterState() {
		return registerState;
	}

	public void setRegisterState(RegisterState registerState) {
		this.registerState = registerState;
	}

}
