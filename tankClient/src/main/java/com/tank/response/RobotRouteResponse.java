package com.tank.response;

import java.io.Serializable;
import java.util.LinkedList;

import com.tank.entity.Tank;

/**
 * 机器坦克的寻路结果
 * 
 * @author Administrator
 *
 */
public class RobotRouteResponse implements Serializable {
	private String tankId;
	private LinkedList<Tank> route = new LinkedList<>();

	public RobotRouteResponse(LinkedList<Tank> route) {
		this.route = route;
	}

	public LinkedList<Tank> getRoute() {
		return route;
	}

	public void setRoute(LinkedList<Tank> route) {
		this.route = route;
	}

	public String getTankId() {
		return tankId;
	}

	public void setTankId(String tankId) {
		this.tankId = tankId;
	}

}
