package com.tank.service;

import java.util.concurrent.CopyOnWriteArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tank.entity.Brick;
import com.tank.entity.GameResource;
import com.tank.entity.Iron;
import com.tank.entity.RobotTank;
import com.tank.entity.Tank;
import com.tank.entity.Water;
import com.tank.request.TankLocationRequest;
import com.tank.response.TankLocationResponse;
import com.tank.server.handler.TankServer;

import io.netty.channel.Channel;

public class TankService {
	private static Logger log = LoggerFactory.getLogger(TankService.class);

	/**
	 * 处理位置请求
	 * 
	 * @param request
	 */
	public void handleTankLocation(TankLocationRequest request) {
		String roomId = request.getRoomId();
		// 更新服务端地图信息
		if (roomId != null) { // 如果游戏未结束，房间未被销毁
			this.updateTankLocationChange(roomId, request.getTankId(), request.getX(), request.getY(),
					request.getDirect());
			TankLocationResponse response = new TankLocationResponse(request);
			log.debug("SEND MSF"+response);
			if (TankServer.ROOM_CHANNELGROUP.get(roomId) != null) {
				for (Channel channel : TankServer.ROOM_CHANNELGROUP.get(roomId).values()) {
					channel.writeAndFlush(response);
				}
			}
		}

	}

	/**
	 * 更新对应的坦克的位置
	 * 
	 * @param roomId
	 * @param tankId
	 * @param x
	 * @param y
	 */
	public void updateTankLocationChange(String roomId, String tankId, int x, int y, int direct) {
		if (tankId.startsWith("robot")) { // 如果收到的是机器坦克的位置信息
			if (TankServer.RESOURCE.get(roomId) != null
					&& TankServer.RESOURCE.get(roomId).getRobotTanks().get(tankId) != null) {
				RobotTank robot = TankServer.RESOURCE.get(roomId).getRobotTanks().get(tankId);
				robot.setX(x);
				robot.setY(y);
				robot.setDirect(direct);
			}
		} else if (TankServer.RESOURCE.get(roomId) != null
				&& TankServer.RESOURCE.get(roomId).getPlayerTanks().get(tankId) != null) {
			// 收到该位置信息的时候，该坦克可能已经被击毁,或者游戏已经结束房间已经被销毁
			Tank tank = TankServer.RESOURCE.get(roomId).getPlayerTanks().get(tankId);
			tank.setX(x);
			tank.setY(y);
			tank.setDirect(direct);
		}
	}

	/**
	 * 坦克死亡,删除该坦克
	 * 
	 * @param roomId
	 * @param tankId
	 */
	public void removeTank(String roomId, String tankId) {
		if (tankId.startsWith("robot")) { // 如果是击中机器坦克
			TankServer.RESOURCE.get(roomId).getRobotTanks().remove(tankId);
		} else { // 如果击中玩家坦克
			TankServer.RESOURCE.get(roomId).getPlayerTanks().remove(tankId);
		}
	}

	/**
	 * 判断我的坦克是否与地形碰撞
	 * 
	 * @param myTank
	 * @param map
	 */
	public void judgeCrash(String roomId, String tankId, int direct) {
		if (roomId == null) {
			return;
		}
		GameResource resource = TankServer.RESOURCE.get(roomId);
		if (resource == null) {
			return;
		}
		Tank tank = resource.getPlayerTanks().get(tankId);
		if (tank == null)
			return;
		tank.setDirect(direct);
		// 判断一下部是否会发生碰撞
		Tank myTank = new Tank(tank.getX(), tank.getY(), direct, "");
		myTank.setDirect(direct);
		myTank.setCrash(false);
		CopyOnWriteArrayList<Brick> bricks = resource.getBricks();
		CopyOnWriteArrayList<Water> waters = resource.getWaters();
		CopyOnWriteArrayList<Iron> irons = resource.getIrons();
		for (Tank t : resource.getPlayerTanks().values()) { // 判断我的坦克是否与其他坦克重叠
			if (t != tank) {
				if (myTank.Overlap(t) && t.isAlive()) {
					myTank.setCrash(true);
					break;
				}
			}
		}
		for (RobotTank robot : resource.getRobotTanks().values()) { // 判断我的坦克是否与机器坦克碰撞
			if (myTank.Overlap(robot) && robot.isAlive()) {
				myTank.setCrash(true);
				break;
			}
		}
		for (int j = 0; j < bricks.size(); j++) { // 判断我的坦克是否与砖块重叠
			if (myTank.Overlap(bricks.get(j)) == true) {
				myTank.setCrash(true);
				break;
			}
		}
		for (int j = 0; j < irons.size(); j++) { // 判断我的坦克是否与铁块重叠
			if (myTank.Overlap(irons.get(j)) == true) {
				myTank.setCrash(true);
				break;
			}
		}
		for (int j = 0; j < waters.size(); j++) { // 判断我的坦克是否与河流重叠
			if (myTank.Overlap(waters.get(j)) == true) {
				myTank.setCrash(true);
				break;
			}
		}
		// 坦克没有与其他物体发生碰撞，就往对应方向移动
		if (direct == Tank.NORTH && myTank.isCrash() == false) {
			tank.goNorth();
		} else if (direct == Tank.SOUTH && myTank.isCrash() == false) {
			tank.goSouth(600);
		} else if (direct == Tank.WEST && myTank.isCrash() == false) {
			tank.goWest(600);
		} else if (direct == Tank.EAST && myTank.isCrash() == false) {
			tank.goEast(600, 600);
		}
		TankLocationResponse tankLocationResponse = new TankLocationResponse();
		tankLocationResponse.setTankId(tankId);
		tankLocationResponse.setX(tank.getX());
		tankLocationResponse.setY(tank.getY());
		tankLocationResponse.setDirect(direct);
		for (Channel channel : TankServer.ROOM_CHANNELGROUP.get(roomId).values()) {
			channel.writeAndFlush(tankLocationResponse);
		}
	}
}
