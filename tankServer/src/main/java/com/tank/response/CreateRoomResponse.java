package com.tank.response;

import java.io.Serializable;

/**
 * 创建房间
 * @author Administrator
 *
 */
public class CreateRoomResponse implements Serializable {
	private String roomId;

	public String getRoomId() {
		return roomId;
	}

	public void setRoomId(String roomId) {
		this.roomId = roomId;
	}

}
