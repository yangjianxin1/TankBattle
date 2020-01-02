package com.tank.entity;

import java.io.Serializable;

public class RobotTank extends Tank implements Serializable{

	public static final int WARNRANGE=200;
	private int circleCenterX;
	private int circleCenterY;

	public RobotTank(Integer x, Integer y, Integer direct,String tankId) {
		super(x, y, direct,tankId);
		this.circleCenterX=x;
		this.circleCenterY=y;
	}

	public int getCircleCenterX() {
		return circleCenterX;
	}

	public void setCircleCenterX(int circleCenterX) {
		this.circleCenterX = circleCenterX;
	}

	public int getCircleCenterY() {
		return circleCenterY;
	}

	public void setCircleCenterY(int circleCenterY) {
		this.circleCenterY = circleCenterY;
	}
	
	


}
