package com.tank.request;

import java.io.Serializable;

/**
 * 坦克被击中的消息
 * 
 * @author Administrator
 *
 */
public class DestroyTankRequest implements Serializable {

	public DestroyTankRequest(String tankId, String roomId) {
		this.tankId = tankId;
		this.roomId = roomId;
	}

	private String tankId;
	private String roomId;

	public String getTankId() {
		return tankId;
	}

	public void setTankId(String tankId) {
		this.tankId = tankId;
	}

	public String getRoomId() {
		return roomId;
	}

	public void setRoomId(String roomId) {
		this.roomId = roomId;
	}

}
