package com.tank.request;

import java.io.Serializable;

/**
 * 发射子弹报文
 * 
 * @author young
 *
 */
public class CreateBulletRequest implements Serializable {
	private int x;
	private int y;
	private int movingDirect; // 子弹运行方向
	private String tankId; // 发射该子弹的坦克的id
	private String roomId; // 房间id

	public CreateBulletRequest(int x, int y, int movingDirect, String tankId, String roomId) {
		this.x = x;
		this.y = y;
		this.movingDirect = movingDirect;
		this.tankId = tankId;
		this.roomId = roomId;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getMovingDirect() {
		return movingDirect;
	}

	public void setMovingDirect(int movingDirect) {
		this.movingDirect = movingDirect;
	}

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
