package com.tank.request;

import java.io.Serializable;

public class SavePogressRequest implements Serializable {
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

	@Override
	public String toString() {
		return "SavePogressRequest [tankId=" + tankId + ", roomId=" + roomId + "]";
	}

}
