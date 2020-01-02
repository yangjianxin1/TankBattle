package com.tank.request;

import java.io.Serializable;

/**
 * 击中砖块,砖块消失的报文
 * 
 * @author young
 *
 */
public class DestroyBrickRequest implements Serializable {
	private int x;
	private int y;
	private String roomId;

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

	public String getRoomId() {
		return roomId;
	}

	public void setRoomId(String roomId) {
		this.roomId = roomId;
	}

}
