package com.tank.request;

import java.io.Serializable;

/**
 * 坦克移动的信息
 * 
 * @author young
 *
 */
public class TankLocationRequest implements Serializable {
	private Integer x;
	private Integer y;
	private String tankId;
	private int direct;
	private String roomId;

	public TankLocationRequest(Integer x, Integer y, String tankId, int direct, String roomId) {
		this.x = x;
		this.y = y;
		this.tankId = tankId;
		this.direct = direct;
		this.roomId = roomId;
	}

	public Integer getX() {
		return x;
	}

	public void setX(Integer x) {
		this.x = x;
	}

	public Integer getY() {
		return y;
	}

	public void setY(Integer y) {
		this.y = y;
	}

	public String getTankId() {
		return tankId;
	}

	public void setTankId(String tankId) {
		this.tankId = tankId;
	}

	public int getDirect() {
		return direct;
	}

	public void setDirect(int direct) {
		this.direct = direct;
	}

	public String getRoomId() {
		return roomId;
	}

	public void setRoomId(String roomId) {
		this.roomId = roomId;
	}

}
