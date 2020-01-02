package com.tank.entity;

import java.io.Serializable;

public class Bullet extends Element implements Serializable{

	private int movingDirect; // 子弹运行方向
	private int speed = 4; // 子弹运行速度
	private String bulletId;
	public static final int RANGE=400;	//子弹射程
	public static final int EXPLOSIONG_RANGE=35;	//子弹爆炸的宽度
	

	public Bullet(Integer x, Integer y, Integer direct) {
		super(x, y);
		this.setMovingDirect(direct);// 设置子弹的运行方向
		// TODO Auto-generated constructor stub
	}

	public int getMovingDirect() {
		return movingDirect;
	}

	public void setMovingDirect(int movingDirect) {
		this.movingDirect = movingDirect;
	}

	public int getSpeed() {
		return speed;
	}

	public void setSpeed(int speed) {
		this.speed = speed;
	}

	public String getBulletId() {
		return bulletId;
	}

	public void setBulletId(String bulletId) {
		this.bulletId = bulletId;
	}
	
	

}
