package com.tank.response;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

import com.tank.entity.Brick;
import com.tank.entity.RobotTank;
import com.tank.entity.Tank;
import com.tank.response.ReConnectResponse.LostConnectState;

/**
 * 
 * @author Administrator
 *
 */
public class ReConnectResponse implements Serializable {
	private LostConnectState lostConnectState;// 断线的时候，玩家的状态
	private Map<String, RobotTank> robotTanks; // 电脑玩家
	private Map<String, Tank> playerTanks; // 所有玩家的坦克
	private List<Brick> bricks;
	private boolean isOver; // 游戏是否结束

	public static enum LostConnectState {
		InGame, NotInGame;
	}

	public LostConnectState getLostConnectState() {
		return lostConnectState;
	}

	public void setLostConnectState(LostConnectState lostConnectState) {
		this.lostConnectState = lostConnectState;
	}

	public Map<String, RobotTank> getRobotTanks() {
		return robotTanks;
	}

	public void setRobotTanks(Map<String, RobotTank> robotTanks) {
		this.robotTanks = robotTanks;
	}

	public Map<String, Tank> getPlayerTanks() {
		return playerTanks;
	}

	public void setPlayerTanks(Map<String, Tank> playerTanks) {
		this.playerTanks = playerTanks;
	}

	public List<Brick> getBricks() {
		return bricks;
	}

	public void setBricks(List<Brick> bricks) {
		this.bricks = bricks;
	}

	public boolean isOver() {
		return isOver;
	}

	public void setOver(boolean isOver) {
		this.isOver = isOver;
	}

}
