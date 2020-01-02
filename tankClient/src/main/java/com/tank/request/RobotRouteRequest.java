package com.tank.request;

import java.io.Serializable;

/**
 * 寻路请求
 * 
 * @author Administrator
 *
 */
public class RobotRouteRequest implements Serializable {
	private String roomId;	//房间号
	private String fromTankId; // 起始坦克的id
	private String toTankId; // 终止位置的的id
	private int mapWidth; // 地图的宽度
	private int mapHeight; // 地图的高度
	private int tankWidth; // 坦克的宽度
	private int speed; // 坦克的速度

	public String getFromTankId() {
		return fromTankId;
	}

	public void setFromTankId(String fromTankId) {
		this.fromTankId = fromTankId;
	}

	public String getToTankId() {
		return toTankId;
	}

	public void setToTankId(String toTankId) {
		this.toTankId = toTankId;
	}

	public String getRoomId() {
		return roomId;
	}

	public void setRoomId(String roomId) {
		this.roomId = roomId;
	}

	public int getMapWidth() {
		return mapWidth;
	}

	public void setMapWidth(int mapWidth) {
		this.mapWidth = mapWidth;
	}

	public int getMapHeight() {
		return mapHeight;
	}

	public void setMapHeight(int mapHeight) {
		this.mapHeight = mapHeight;
	}

	public int getTankWidth() {
		return tankWidth;
	}

	public void setTankWidth(int tankWidth) {
		this.tankWidth = tankWidth;
	}

	public int getSpeed() {
		return speed;
	}

	public void setSpeed(int speed) {
		this.speed = speed;
	}

}
