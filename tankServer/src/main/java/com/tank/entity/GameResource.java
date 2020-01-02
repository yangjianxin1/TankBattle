package com.tank.entity;

import java.io.Serializable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

import com.tank.map.Map;

/**
 * 游戏的资源
 * 
 * @author young
 *
 */
public class GameResource implements Serializable {

	// private CopyOnWriteArrayList<RobotTank> robotTanks = new
	// CopyOnWriteArrayList<>(); //电脑玩家
	private ConcurrentHashMap<String, RobotTank> robotTanks = new ConcurrentHashMap<>(); // 电脑玩家
	private ConcurrentHashMap<String, Tank> playerTanks = new ConcurrentHashMap<>(); // 所有玩家的坦克
	private CopyOnWriteArrayList<Brick> bricks;
	private CopyOnWriteArrayList<Iron> irons;
	private CopyOnWriteArrayList<Water> waters;
	private int mapId; // 关卡编号
	private boolean type;	//该局游戏是pvp还是pvc的标识.true:pvp,false:pvc

	public CopyOnWriteArrayList<Brick> getBricks() {
		return bricks;
	}

	public void setBricks(CopyOnWriteArrayList<Brick> bricks) {
		this.bricks = bricks;
	}

	public CopyOnWriteArrayList<Iron> getIrons() {
		return irons;
	}

	public void setIrons(CopyOnWriteArrayList<Iron> irons) {
		this.irons = irons;
	}

	public CopyOnWriteArrayList<Water> getWaters() {
		return waters;
	}

	public void setWaters(CopyOnWriteArrayList<Water> waters) {
		this.waters = waters;
	}

	public ConcurrentHashMap<String, RobotTank> getRobotTanks() {
		return robotTanks;
	}

	public void setRobotTanks(ConcurrentHashMap<String, RobotTank> robotTanks) {
		this.robotTanks = robotTanks;
	}

	public ConcurrentHashMap<String, Tank> getPlayerTanks() {
		return playerTanks;
	}

	public void setPlayerTanks(ConcurrentHashMap<String, Tank> playerTanks) {
		this.playerTanks = playerTanks;
	}

	public int getMapId() {
		return mapId;
	}

	public void setMapId(int mapId) {
		this.mapId = mapId;
	}

	public boolean isType() {
		return type;
	}

	public void setType(boolean type) {
		this.type = type;
	}
	

}
