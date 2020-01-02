package com.tank.request;

import java.io.Serializable;

/**
 * 销毁房间
 * 
 * @author Administrator
 *
 */
public class DestroyRoomRequest implements Serializable {
	private String roomId;

	public DestroyRoomRequest(String roomId) {
		this.roomId = roomId;
	}

	public String getRoomId() {
		return roomId;
	}

	public void setRoomId(String roomId) {
		this.roomId = roomId;
	}

}
