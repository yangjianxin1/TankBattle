package com.tank.response;

import java.io.Serializable;

/**
 * 返回的坦克id消息,tankId唯一
 * @author young
 *
 */
public class TankIdResponse implements Serializable{

	private String tankId;

	public String getTankId() {
		return tankId;
	}

	public void setTankId(String tankId) {
		this.tankId = tankId;
	}
	
}
