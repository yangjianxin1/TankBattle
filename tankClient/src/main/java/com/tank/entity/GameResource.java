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
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int gameOverY = 0; // gameOver的图片的y坐标
	private int gameSuccessY = 0; // 游戏获胜的图片的y坐标

	// private MyTank myTank;
	private CopyOnWriteArrayList<Bomb> bombs = new CopyOnWriteArrayList<>();
	private ConcurrentHashMap<String, RobotTank> robotTanks = new ConcurrentHashMap<>(); //电脑玩家
	private ConcurrentHashMap<String, Tank> playerTanks = new ConcurrentHashMap<>(); // 所有玩家的坦克
	private Map map;

	public CopyOnWriteArrayList<Bomb> getBombs() {
		return bombs;
	}

	public void setBombs(CopyOnWriteArrayList<Bomb> bombs) {
		this.bombs = bombs;
	}

	public Map getMap() {
		return map;
	}

	public void setMap(Map map) {
		this.map = map;
	}

	// public MyTank getMyTank() {
	// return myTank;
	// }
	//
	// public void setMyTank(MyTank myTank) {
	// this.myTank = myTank;
	// }

	

	public ConcurrentHashMap<String, Tank> getPlayerTanks() {
		return playerTanks;
	}

	
	public ConcurrentHashMap<String, RobotTank> getRobotTanks() {
		return robotTanks;
	}

	public void setRobotTanks(ConcurrentHashMap<String, RobotTank> robotTanks) {
		this.robotTanks = robotTanks;
	}

	public void setPlayerTanks(ConcurrentHashMap<String, Tank> playerTanks) {
		this.playerTanks = playerTanks;
	}

	public int getGameOverY() {
		return gameOverY;
	}

	public void setGameOverY(int gameOverY) {
		this.gameOverY = gameOverY;
	}

	public int getGameSuccessY() {
		return gameSuccessY;
	}

	public void setGameSuccessY(int gameSuccessY) {
		this.gameSuccessY = gameSuccessY;
	}

	

}
