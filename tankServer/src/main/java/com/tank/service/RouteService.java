package com.tank.service;

import java.util.Comparator;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Set;

import com.tank.entity.Brick;
import com.tank.entity.GameResource;
import com.tank.entity.Iron;
import com.tank.entity.Position;
import com.tank.entity.Tank;
import com.tank.entity.Water;
import com.tank.response.RobotRouteResponse;
import com.tank.server.handler.TankServer;

public class RouteService {

	/**
	 * 规划路径
	 * 
	 * @param roomId
	 * @param fromTankId
	 * @param toTankId
	 * @param mapWidth
	 * @param mapHeight
	 * @param tankWidth
	 * @param speed
	 * @return
	 */
	public RobotRouteResponse findRoute1(String roomId, String fromTankId, String toTankId, int mapWidth, int mapHeight,
			int tankWidth, int speed) {
		if(roomId==null){	//游戏结束后，房间被销毁，roomId为null
			return null;
		}
		GameResource resource = TankServer.RESOURCE.get(roomId);
		if(resource==null){	//游戏结束后，仍然收到迟到的路径规划的请求报文
			return null;
		}
		Tank startTank = resource.getRobotTanks().get(fromTankId);
		Tank endTank = resource.getPlayerTanks().get(toTankId);
		if(endTank==null){
			return null;
		}
		if(startTank==null){
			return null;
		}
		Set<Position> closeTable = new HashSet<>(); // closed表
		Comparator<Position> comp = new Comparator<Position>() {

			@Override
			public int compare(Position p1, Position p2) {
				return new Double(p1.getF()).compareTo(new Double(p2.getF()));
			}
		};
		PriorityQueue<Position> opentable = new PriorityQueue<>(comp); // open表

		Position startPosition = new Position(startTank.getX(), startTank.getY()); // 起始点
		Position endPosition = new Position(endTank.getX(), endTank.getY()); // 终点
		startPosition.setDirect(startTank.getDirect());
		startPosition.setH(h(startPosition, endPosition));
		startPosition.setG(0);
		startPosition.setF(h(startPosition, endPosition));
		opentable.add(startPosition);

		boolean finish = false; // 寻路结束的标识
		final int[][] directs = { { 0, -speed }, { 0, speed }, { -speed, 0 }, { speed, 0 } }; // 可以扩展的四个方向
		Position lastPosition = null;
		List<Brick> bricks = resource.getBricks();
		List<Water> waters = resource.getWaters();
		List<Iron> irons = resource.getIrons();
		while (!opentable.isEmpty() && !finish) {
			Position p = opentable.poll(); // 取f最小的节点进行扩展
			closeTable.add(p); // 将已扩展的节点放入closed表
			for (int direct = 0; direct < 4; direct++) { // 判断坦克往四个方向会不会碰到障碍物，如果会碰撞就不进行扩展

				Position newPosition = new Position(p.getX() + directs[direct][0], p.getY() + directs[direct][1]);
				Tank robot = new Tank(p.getX(), p.getY(), direct, "");
				// 如果坦克的下一步，没有跑出地图
				if (newPosition.getX() >= tankWidth && newPosition.getX() <= mapWidth - tankWidth
						&& newPosition.getY() >= tankWidth && newPosition.getY() < mapWidth - tankWidth
						&& !closeTable.contains(newPosition) && !opentable.contains(newPosition)) {
					// 如果已将到达终点,也就是和目标坦克发生碰撞,并且发射子弹能击中终点坦克
					if (robot.Overlap(endTank)&& (Math.abs(endTank.getX() - robot.getX()) < robot.getWidth() / 2||Math.abs(endTank.getY() - robot.getY()) < robot.getWidth() / 2)) {
						lastPosition = p;
						// lastPosition.setLast(p);
						finish = true;
						break;
					}
					// 判断robot往direct方向走，会不会碰到砖块、水或者钢铁，如果会碰撞，该direct的下一个坐标就不进行扩展
					for (int j = 0; j < bricks.size(); j++) { // 判断我的坦克是否与砖块重叠
						if (robot.Overlap(bricks.get(j)) == true) {
							robot.setCrash(true);
							break;
						}
					}
					for (int j = 0; j < irons.size(); j++) { // 判断我的坦克是否与铁块重叠
						if (robot.Overlap(irons.get(j)) == true) {
							robot.setCrash(true);
							break;
						}
					}
					for (int j = 0; j < waters.size(); j++) { // 判断我的坦克是否与河流重叠
						if (robot.Overlap(waters.get(j)) == true) {
							robot.setCrash(true);
							break;
						}
					}
					if (robot.isCrash()) { // 如果坦克在该坐标，该方向的下一个扩展点会发生碰撞，则该扩展点无效
						continue;
					} else {
						newPosition.setDirect(direct);
						newPosition.setLast(p);
						newPosition.setG(p.getG() + speed);
						newPosition.setH(h(newPosition, endPosition));
						newPosition.setF(newPosition.getH() + newPosition.getG());
						opentable.add(newPosition); // 最小的耗散值的节点进入open表
					}
				}
			}
		}
		LinkedList<Tank> list = new LinkedList<>();
		while (lastPosition != null) {
			Tank t = new Tank(lastPosition.getX(), lastPosition.getY(), lastPosition.getDirect(),
					startTank.getTankId());
			list.addFirst(t);
			lastPosition = lastPosition.getLast();
		}
		RobotRouteResponse response = new RobotRouteResponse(list);
		response.setTankId(fromTankId);
		return response;
	}

	/**
	 * 终点设为，发射子弹能击中目标坦克，或者和目标坦克碰撞
	 * 
	 * @param roomId
	 * @param fromTankId
	 * @param toTankId
	 * @param mapWidth
	 * @param mapHeight
	 * @param tankWidth
	 * @param speed
	 * @return
	 */
	public RobotRouteResponse findRoute2(String roomId, String fromTankId, String toTankId, int mapWidth, int mapHeight,
			int tankWidth, int speed) {
		if( TankServer.RESOURCE.get(roomId)==null){	//游戏结束后，仍然收到迟到的路径规划的请求报文
			return null;
		}
		GameResource resource = TankServer.RESOURCE.get(roomId);
		Tank startTank = resource.getRobotTanks().get(fromTankId);
		Tank endTank = resource.getPlayerTanks().get(toTankId);
		Set<Position> closeTable = new HashSet<>(); // closed表
		Comparator<Position> comp = new Comparator<Position>() {

			@Override
			public int compare(Position p1, Position p2) {
				return new Double(p1.getF()).compareTo(new Double(p2.getF()));
			}
		};
		PriorityQueue<Position> opentable = new PriorityQueue<>(comp); // open表

		Position startPosition = new Position(startTank.getX(), startTank.getY()); // 起始点
		Position endPosition = new Position(endTank.getX(), endTank.getY()); // 终点
		startPosition.setDirect(startTank.getDirect());
		startPosition.setH(h(startPosition, endPosition));
		startPosition.setG(0);
		startPosition.setF(h(startPosition, endPosition));
		opentable.add(startPosition);

		boolean finish = false; // 寻路结束的标识
		final int[][] directs = { { 0, -speed }, { 0, speed }, { -speed, 0 }, { speed, 0 } }; // 可以扩展的四个方向
		Position lastPosition = null;
		List<Brick> bricks = resource.getBricks();
		List<Water> waters = resource.getWaters();
		List<Iron> irons = resource.getIrons();
		while (!opentable.isEmpty() && !finish) {
			Position p = opentable.poll(); // 取f最小的节点进行扩展
			closeTable.add(p); // 将已扩展的节点放入closed表
			Tank robot = new Tank(p.getX(), p.getY(), -1, "");
			//如果在该点能直接击中坦克
			if (Math.abs(endTank.getX() - robot.getX()) < robot.getWidth() / 2
					&& endTank.getY() < robot.getY()) { // 如果机器坦克在下面
				boolean ironFlag = false; // 路径中是否有钢铁的标识
				for (Iron iron : resource.getIrons()) { // 子弹路径上，有钢铁
					if (Math.abs(iron.getX() - robot.getX()) < iron.getWidth() / 2&&iron.getY()<robot.getY()&&iron.getY()>endTank.getY()) {
						ironFlag = true;
						break;
					}
				}
				if (ironFlag == false) {
					p.setDirect(Tank.NORTH);
					lastPosition=p;
					finish = true;
				}
			}else if (Math.abs(endTank.getX() - robot.getX()) < robot.getWidth() / 2
					&& endTank.getY() > robot.getY()) { // 如果机器坦克在上面
				boolean ironFlag = false; // 路径中是否有钢铁的标识
				for (Iron iron : resource.getIrons()) { // 子弹路径上，不会有钢铁
					if (Math.abs(iron.getX() - robot.getX()) < iron.getWidth() / 2&&iron.getY()>robot.getY()&&iron.getY()<endTank.getY()) {
						ironFlag = true;
						break;
					}
				}
				if (ironFlag == false) {
					p.setDirect(Tank.SOUTH);
					lastPosition=p;
					finish = true;
				}
			}else if (Math.abs(endTank.getY() - robot.getY()) < robot.getWidth() / 2
					&& endTank.getX() > robot.getX()) { // 如果机器坦克在左边
				boolean ironFlag = false; // 路径中是否有钢铁的标识
				for (Iron iron : resource.getIrons()) { // 子弹路径上，不会有钢铁
					if (Math.abs(iron.getY() - robot.getY()) < iron.getWidth() / 2&&iron.getX()>robot.getX()&&iron.getX()<endTank.getX()) {
						ironFlag = true;
						break;
					}
				}
				if (ironFlag == false) {
					p.setDirect(Tank.EAST);
					lastPosition=p;
					finish = true;
				}
			}else if (Math.abs(endTank.getY() - robot.getY()) < robot.getWidth() / 2
					&& endTank.getX() < robot.getX()) { // 如果机器坦克在右边
				boolean ironFlag = false; // 路径中是否有钢铁的标识
				for (Iron iron : resource.getIrons()) { // 子弹路径上，不会有钢铁
					if (Math.abs(iron.getY() - robot.getY()) < iron.getWidth() / 2&&iron.getX()<robot.getX()&&iron.getX()>endTank.getX()) {
						ironFlag = true;
						break;
					}
				}
				if (ironFlag == false) {
					p.setDirect(Tank.WEST);
					lastPosition=p;
					finish = true;
				}
			}
			if (finish == true) {
				break;
			}
			
			for (int direct = 0; direct < 4; direct++) { // 判断坦克往四个方向会不会碰到障碍物，如果会碰撞就不进行扩展
				robot.setDirect(direct);
				Position newPosition = new Position(p.getX() + directs[direct][0], p.getY() + directs[direct][1]);
				
				// 如果坦克的下一步，没有跑出地图
				if (newPosition.getX() >= tankWidth && newPosition.getX() <= mapWidth - tankWidth
						&& newPosition.getY() >= tankWidth && newPosition.getY() < mapWidth - tankWidth
						&& !closeTable.contains(newPosition) && !opentable.contains(newPosition)) {
					// 和目标坦克发生碰撞
					if (robot.Overlap(endTank)) {
						continue;
					}
					if (finish == true) {
						break;
					}
					// 判断robot往direct方向走，会不会碰到砖块、水或者钢铁，如果会碰撞，该direct的下一个坐标就不进行扩展
					for (int j = 0; j < bricks.size(); j++) { // 判断我的坦克是否与砖块重叠
						if (robot.Overlap(bricks.get(j)) == true) {
							robot.setCrash(true);
							break;
						}
					}
					for (int j = 0; j < irons.size(); j++) { // 判断我的坦克是否与铁块重叠
						if (robot.Overlap(irons.get(j)) == true) {
							robot.setCrash(true);
							break;
						}
					}
					for (int j = 0; j < waters.size(); j++) { // 判断我的坦克是否与河流重叠
						if (robot.Overlap(waters.get(j)) == true) {
							robot.setCrash(true);
							break;
						}
					}
					if (robot.isCrash()) { // 如果坦克在该坐标，该方向的下一个扩展点会发生碰撞，则该扩展点无效
						continue;
					} else {
						newPosition.setDirect(direct);
						newPosition.setLast(p);
						newPosition.setG(p.getG() + speed);
						newPosition.setH(h(newPosition, endPosition));
						newPosition.setF(newPosition.getH() + newPosition.getG());
						opentable.add(newPosition); // 最小的耗散值的节点进入open表
					}
				}
			}
		}
		LinkedList<Tank> list = new LinkedList<>();
		while (lastPosition != null) {
			Tank t = new Tank(lastPosition.getX(), lastPosition.getY(), lastPosition.getDirect(),
					startTank.getTankId());
			list.addFirst(t);
			lastPosition = lastPosition.getLast();
		}
		RobotRouteResponse response = new RobotRouteResponse(list);
		response.setTankId(fromTankId);
		return response;
	}

	double h(Position pnt, Position endPosition) {
		return hEuclidianDistance(pnt, endPosition);
	}

	/**
	 * 欧式距离,小于等于实际值
	 */
	double hEuclidianDistance(Position pnt, Position endPosition) {
		return Math.sqrt(Math.pow(pnt.getX() - endPosition.getX(), 2) + Math.pow(pnt.getY() - endPosition.getY(), 2));
	}

}
