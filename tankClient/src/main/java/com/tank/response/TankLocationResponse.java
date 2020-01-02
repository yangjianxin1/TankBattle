package com.tank.response;

import java.io.Serializable;

import com.tank.request.TankLocationRequest;

/**
 * 坦克移动的信息
 * 
 * @author young
 *
 */
public class TankLocationResponse implements Serializable {
	private Integer x;
	private Integer y;
	private String tankId;
	private Integer direct;

	public TankLocationResponse(TankLocationRequest request) {
		this.setX(request.getX());
		this.setY(request.getY());
		this.setTankId(request.getTankId());
		this.setDirect(request.getDirect());
	}

	public TankLocationResponse() {

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

	public Integer getDirect() {
		return direct;
	}

	public void setDirect(Integer direct) {
		this.direct = direct;
	}

}
