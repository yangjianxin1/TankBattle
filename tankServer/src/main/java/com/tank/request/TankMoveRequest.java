package com.tank.request;

import java.io.Serializable;

/**
 * 坦克移动的信息
 * 
 * @author young
 *
 */
public class TankMoveRequest implements Serializable {
	private String tankId;
	private String roomId;
	private int movingDirect; // 移动方向

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

	public int getMovingDirect() {
		return movingDirect;
	}

	public void setMovingDirect(int movingDirect) {
		this.movingDirect = movingDirect;
	}

	@Override
	public String toString() {
		return "TankMoveRequest [tankId=" + tankId + ", roomId=" + roomId + ", movingDirect=" + movingDirect + "]";
	}

	
}
