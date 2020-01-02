package com.tank.request;

import java.io.Serializable;

/**
 * 退出房间的请求
 * 
 * @author Administrator
 *
 */
public class ExitRoomRequest implements Serializable {

	private String roomId;
	private String tankId;

	public ExitRoomRequest(String roomId, String tankId) {
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

}
