package com.tank.response;

import java.io.Serializable;
import java.util.List;

public class RoomResponse implements Serializable {
	private String roomId; // 房间号
	private List<String> playerIds; // 该房间内的所有玩家的id号
	private RoomState roomState;

	public static enum RoomState {
		RoomExist, RoomNoExist,GameIsSatrted;
	}

	public String getRoomId() {
		return roomId;
	}

	public void setRoomId(String roomId) {
		this.roomId = roomId;
	}

	public List<String> getPlayerIds() {
		return playerIds;
	}

	public void setPlayerIds(List<String> playerIds) {
		this.playerIds = playerIds;
	}

	public RoomState getRoomState() {
		return roomState;
	}

	public void setRoomState(RoomState roomState) {
		this.roomState = roomState;
	}

}
