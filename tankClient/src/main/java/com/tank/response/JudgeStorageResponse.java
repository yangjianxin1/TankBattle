package com.tank.response;

import java.io.Serializable;

/**
 * 某个关卡是否有存档的响应
 * 
 * @author Administrator
 *
 */
public class JudgeStorageResponse implements Serializable {
	private int playerId;
	private int mapId;
	private boolean storageExist;

	public boolean isStorageExist() {
		return storageExist;
	}

	public void setStorageExist(boolean storageExist) {
		this.storageExist = storageExist;
	}

	public int getPlayerId() {
		return playerId;
	}

	public void setPlayerId(int playerId) {
		this.playerId = playerId;
	}

	public int getMapId() {
		return mapId;
	}

	public void setMapId(int mapId) {
		this.mapId = mapId;
	}

}
