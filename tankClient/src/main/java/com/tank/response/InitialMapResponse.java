package com.tank.response;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.tank.entity.Brick;
import com.tank.entity.Iron;
import com.tank.entity.RobotTank;
import com.tank.entity.Tank;
import com.tank.entity.Water;
import com.tank.response.InitialMapResponse.GameMode;

/**
 * 地图初始化响应
 * 
 * @author young
 *
 */
public class InitialMapResponse implements Serializable {
	private List<Brick> bricks;
	private List<Water> waters;
	private List<Iron> irons;
	private Map<String, Tank> playerTanks;
	private Map<String, RobotTank> robots;
	private GameMode gameMode;
	private String roomId;

	public static enum GameMode {
		PVC, PVP;
	}

	public List<Brick> getBricks() {
		return bricks;
	}

	public void setBricks(List<Brick> bricks) {
		this.bricks = bricks;
	}

	public List<Water> getWaters() {
		return waters;
	}

	public void setWaters(List<Water> waters) {
		this.waters = waters;
	}

	public List<Iron> getIrons() {
		return irons;
	}

	public void setIrons(List<Iron> irons) {
		this.irons = irons;
	}

	public Map<String, Tank> getPlayerTanks() {
		return playerTanks;
	}

	public void setPlayerTanks(Map<String, Tank> playerTanks) {
		this.playerTanks = playerTanks;
	}

	

	public Map<String, RobotTank> getRobots() {
		return robots;
	}

	public void setRobots(Map<String, RobotTank> robots) {
		this.robots = robots;
	}

	public GameMode getGameMode() {
		return gameMode;
	}

	public void setGameMode(GameMode gameMode) {
		this.gameMode = gameMode;
	}

	public String getRoomId() {
		return roomId;
	}

	public void setRoomId(String roomId) {
		this.roomId = roomId;
	}

}
