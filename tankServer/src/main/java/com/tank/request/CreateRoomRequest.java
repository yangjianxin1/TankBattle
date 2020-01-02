package com.tank.request;

import java.io.Serializable;

/**
 * 创建房间
 * 
 * @author Administrator
 *
 */
public class CreateRoomRequest implements Serializable {
	private String userId;

	public CreateRoomRequest(String userId) {
		this.userId = userId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	@Override
	public String toString() {
		return "CreateRoomRequest [userId=" + userId + "]";
	}

	
}
