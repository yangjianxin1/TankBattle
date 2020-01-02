package com.tank.request;

import java.io.Serializable;

/**
 * 开始游戏的请求
 * 
 * @author young
 *
 */
public class StartGameRequest implements Serializable {
	private String roomId;
	private String tankId;
	private GameMode gameMode;
	private int chapter;
	private boolean initMap; // 这个字段只用于pvc模式，true：表示加载原始的地图，false：加载存档的地图

	public static enum GameMode {
		PVC, PVP;
	}

	public StartGameRequest(String roomId) {
		this.roomId = roomId;
	}

	public String getRoomId() {
		return roomId;
	}

	public void setRoomId(String roomId) {
		this.roomId = roomId;
	}

	public GameMode getGameMode() {
		return gameMode;
	}

	public void setGameMode(GameMode gameMode) {
		this.gameMode = gameMode;
	}

	public String getTankId() {
		return tankId;
	}

	public void setTankId(String tankId) {
		this.tankId = tankId;
	}

	public int getChapter() {
		return chapter;
	}

	public void setChapter(int chapter) {
		this.chapter = chapter;
	}

	public boolean isInitMap() {
		return initMap;
	}

	public void setInitMap(boolean initMap) {
		this.initMap = initMap;
	}

	@Override
	public String toString() {
		return "StartGameRequest [roomId=" + roomId + ", tankId=" + tankId + ", gameMode=" + gameMode + ", chapter="
				+ chapter + ", initMap=" + initMap + "]";
	}
	
	

}
