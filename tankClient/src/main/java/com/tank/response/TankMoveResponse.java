package com.tank.response;

import java.io.Serializable;

/**
 * 坦克移动的信息
 * 
 * @author young
 *
 */
public class TankMoveResponse implements Serializable {
	private Integer x;
	private Integer y;
	private String tankId;

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

}
