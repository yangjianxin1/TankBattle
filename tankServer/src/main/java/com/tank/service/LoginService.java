package com.tank.service;

import com.tank.dao.PlayerDao;
import com.tank.response.LoginResponse;
import com.tank.response.LoginResponse.LoginState;

public class LoginService {
	private PlayerDao playerDao = new PlayerDao();

	/**
	 * 登陆
	 * 
	 * @param name
	 * @param password
	 * @return
	 */
	public LoginResponse login(String name, String password) {
		LoginResponse response = new LoginResponse();
		if (playerDao.playerExist(name) == false) { // 账号不存在
			response.setLoginState(LoginState.AccountNotExist);
		} else if (playerDao.validatePassword(name, password) == true) { // 密码正确，登陆成功
			response.setLoginState(LoginState.LoginSuccess);
		} else { // 密码错误登陆失败
			response.setLoginState(LoginState.PasswordError);
		}
		response.setId(name);
		return response;
	}

}