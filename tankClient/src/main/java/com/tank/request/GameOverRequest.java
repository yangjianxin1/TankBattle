package com.tank.request;

import java.io.Serializable;

/**
 * 游戏结束
 * 
 * @author Administrator
 *
 */
public class GameOverRequest implements Serializable {
	private String roomId;

	public GameOverRequest(String roomId) {
		this.roomId = roomId;
	}

	public String getRoomId() {
		return roomId;
	}

	public void setRoomId(String roomId) {
		this.roomId = roomId;
	}

}
