package com.tank.request;

import java.io.Serializable;

/**
 * 进入某个房间的请求
 * 
 * @author Administrator
 *
 */
public class EnterRoomRequest implements Serializable {
	private String roomId; // 房间id
	private String userId; // 用户

	public EnterRoomRequest(String roomId, String userId) {
		this.roomId = roomId;
		this.userId = userId;
	}

	public String getRoomId() {
		return roomId;
	}

	public void setRoomId(String roomId) {
		this.roomId = roomId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	@Override
	public String toString() {
		return "EnterRoomRequest [roomId=" + roomId + ", userId=" + userId + "]";
	}

	
}
