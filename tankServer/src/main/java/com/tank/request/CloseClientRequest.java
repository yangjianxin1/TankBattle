package com.tank.request;

import java.io.Serializable;

/**
 * 当客户端关闭的时候，发送该请求
 * 
 * @author Administrator
 *
 */
public class CloseClientRequest implements Serializable {
	private String roomId;
	private String tankId;

	public CloseClientRequest(String roomId, String tankId) {
		this.roomId = roomId;
		this.tankId = tankId;
	}

	public String getRoomId() {
		return roomId;
	}

	public void setRoomId(String roomId) {
		this.roomId = roomId;
	}

	public String getTankId() {
		return tankId;
	}

	public void setTankId(String tankId) {
		this.tankId = tankId;
	}

	@Override
	public String toString() {
		return "CloseClientRequest [roomId=" + roomId + ", tankId=" + tankId + "]";
	}

	
}
