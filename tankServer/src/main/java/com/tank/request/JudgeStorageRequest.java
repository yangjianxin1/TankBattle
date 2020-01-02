package com.tank.request;

import java.io.Serializable;

/**
 * 判断当前关卡是否有存档
 * 
 * @author Administrator
 *
 */
public class JudgeStorageRequest implements Serializable {
	private String tankId;
	private int mapId;

	public String getTankId() {
		return tankId;
	}

	public void setTankId(String tankId) {
		this.tankId = tankId;
	}

	public int getMapId() {
		return mapId;
	}

	public void setMapId(int mapId) {
		this.mapId = mapId;
	}

	@Override
	public String toString() {
		return "JudgeStorageRequest [tankId=" + tankId + ", mapId=" + mapId + "]";
	}

}
