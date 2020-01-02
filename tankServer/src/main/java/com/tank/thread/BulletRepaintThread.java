package com.tank.thread;

import java.util.concurrent.CopyOnWriteArrayList;

import com.tank.entity.Bomb;
import com.tank.entity.Brick;
import com.tank.entity.Bullet;
import com.tank.entity.Element;
import com.tank.entity.GameResource;
import com.tank.entity.Iron;
import com.tank.entity.RobotTank;
import com.tank.entity.Tank;
import com.tank.request.DestroyBrickRequest;
import com.tank.response.BombResponse;
import com.tank.response.BulletLocationResponse;
import com.tank.response.DestroyBrickResponse;
import com.tank.response.DestroyTankResponse;
import com.tank.response.FailureResponse;
import com.tank.response.VictoryResponse;
import com.tank.server.handler.TankServer;

import io.netty.channel.Channel;

public class BulletRepaintThread implements Runnable {
	private int initX;
	private int initY;
	private Bullet bullet;
	private Tank tank;
	private String roomId;
	private GameResource resource;
	public boolean stopFlag = false;

	public BulletRepaintThread(Bullet bullet, Tank tank, String roomId) {
		this.tank = tank;
		this.bullet = bullet;
		initX = bullet.getX();
		initY = bullet.getY();
		this.roomId = roomId;
		this.resource = TankServer.RESOURCE.get(roomId);
	}

	@Override
	public void run() {

		while (true) {
			if(tank.isAlive()==false){
				break;
			}
			switch (bullet.getMovingDirect()) { // 选择子弹的方向
			case Element.NORTH:
				bullet.setY(bullet.getY() - bullet.getSpeed());
				break;
			case Element.SOUTH:
				bullet.setY(bullet.getY() + bullet.getSpeed());
				break;
			case Element.WEST:
				bullet.setX(bullet.getX() - bullet.getSpeed());
				break;
			case Element.EAST:
				bullet.setX(bullet.getX() + bullet.getSpeed());
				break;
			}
			if (hEuclidianDistance(initX, initY, bullet.getX(), bullet.getY()) >= Bullet.RANGE) { // 子弹运行到爆炸的范围
				afterHit();
				break;
			}
			if (isHit()) {
				afterHit();
				break;
			}
			BulletLocationResponse response = new BulletLocationResponse();
			response.setTankId(tank.getTankId());
			response.setBulletId(bullet.getBulletId());
			response.setX(bullet.getX());
			response.setY(bullet.getY());
			if (TankServer.ROOM_CHANNELGROUP.get(roomId) == null) {
				break;
			}
			for (Channel channel : TankServer.ROOM_CHANNELGROUP.get(roomId).values()) { // 广播子弹信息
				channel.writeAndFlush(response);
			}
			if (bullet.getX() < 5 || bullet.getX() > 600 - 5 || bullet.getY() < 5 || bullet.getY() > 600 - 5) { // 判断子弹是否碰到边界
				afterHit();
				bullet.setAlive(false); // 子弹死亡
				tank.getBullets().remove(bullet);
				break;
			}
			try {
				Thread.sleep(30); // 每隔30毫秒移动一次
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		tank.getBullets().remove(bullet.getBulletId()); // 线程结束，子弹消失，删除子弹
		synchronized (this.tank.getRemainBulletCount()) {
			this.tank.increseRemainBulletCount();
		}
		System.out.println("bullet over");
	}

	/**
	 * 删除爆炸范围内的物体
	 * 
	 * @param explosionCenterX
	 * @param explosionCenterY
	 */
	private void afterHit() {
		BombResponse bombResponse = new BombResponse();
		bombResponse.setX(bullet.getX());
		bombResponse.setY(bullet.getY());
		bombResponse.setTankId(tank.getTankId());
		bombResponse.setBulletId(bullet.getBulletId());
		bombResponse.setWidth(Bullet.EXPLOSIONG_RANGE * 2);
		for (Channel channel : TankServer.ROOM_CHANNELGROUP.get(roomId).values()) { // 广播爆炸信息
			channel.writeAndFlush(bombResponse);
		}

		CopyOnWriteArrayList<Brick> bricks = resource.getBricks();
		for (Brick brick : bricks) {
			if (Math.abs(brick.getY() - bullet.getY()) < Bullet.EXPLOSIONG_RANGE + brick.getWidth() / 2
					&& Math.abs(brick.getX() - bullet.getX()) < Bullet.EXPLOSIONG_RANGE + brick.getWidth() / 2) {
				bricks.remove(brick);
				// 广播击中砖块消息
				DestroyBrickResponse destroyBrickResponse = new DestroyBrickResponse();
				destroyBrickResponse.setX(brick.getX());
				destroyBrickResponse.setY(brick.getY());
				for (Channel channel : TankServer.ROOM_CHANNELGROUP.get(roomId).values()) { // 广播子弹信息
					channel.writeAndFlush(destroyBrickResponse);
				}
			}
		}

		for (Tank playerTank : resource.getPlayerTanks().values()) {
			if (tank != playerTank
					&& Math.abs(playerTank.getY() - bullet.getY()) < Bullet.EXPLOSIONG_RANGE + playerTank.getWidth() / 2
					&& Math.abs(playerTank.getX() - bullet.getX()) < Bullet.EXPLOSIONG_RANGE + playerTank.getWidth() / 2
					&& playerTank.isAlive()) { // 如果该坦克还存活
				playerTank.setAlive(false);
				//被击中的玩家坦克，删除子弹
				for(String key:resource.getPlayerTanks().get(playerTank.getTankId()).getBullets().keySet()){
					resource.getPlayerTanks().get(playerTank.getTankId()).getBullets().remove(key);
				}
				resource.getPlayerTanks().remove(playerTank.getTankId());
				// 广播击毁信息
				DestroyTankResponse destroyTankResponse = new DestroyTankResponse(playerTank.getTankId());
				for (Channel channel : TankServer.ROOM_CHANNELGROUP.get(roomId).values()) { // 广播击毁信息
					channel.writeAndFlush(destroyTankResponse);
				}
				if (TankServer.ROOM_CHANNELGROUP.get(roomId).get(playerTank.getTankId()) != null) { // 如果被击毁的玩家没有关闭游戏，channel仍存在
					TankServer.ROOM_CHANNELGROUP.get(roomId).get(playerTank.getTankId())
							.writeAndFlush(new FailureResponse());
				}
				TankServer.ROOM_CHANNELGROUP.get(roomId).remove(playerTank.getTankId()); // 将channel移出该房间
				TankServer.NAME_ROOMID.remove(playerTank.getTankId());
				// 只有机器坦克数量为0，游戏胜利
				if (resource.getRobotTanks().size() == 0 && resource.getPlayerTanks().size() <= 1) {
					for (Channel channel : TankServer.ROOM_CHANNELGROUP.get(roomId).values()) { // 发送胜利信息
						channel.writeAndFlush(new VictoryResponse());
					}
					// 游戏结束，删除该房间,并且清除该房间的资源
					TankServer.ROOM_CHANNELGROUP.remove(roomId);
					TankServer.RESOURCE.remove(roomId);
					TankServer.isStart.remove(roomId);
					TankServer.MonitorRobotThreads.get(roomId).stopFlag = true;
					TankServer.MonitorRobotThreads.remove(roomId);
				}
				if (resource.getPlayerTanks().size() <= 0) { // 如果玩家坦克全部被击毁
					// 游戏结束，删除该房间,并且清除该房间的资源
					TankServer.ROOM_CHANNELGROUP.remove(roomId);
					TankServer.RESOURCE.remove(roomId);
					TankServer.isStart.remove(roomId);
					TankServer.MonitorRobotThreads.get(roomId).stopFlag = true;
					TankServer.MonitorRobotThreads.remove(roomId);
				}
			}
		}

		if (!tank.getTankId().startsWith("robot")) { // 如果该子弹不是机器坦克产生的，机器坦克的子弹不能击毁机器坦克
			for (RobotTank robot : resource.getRobotTanks().values()) {
				if (Math.abs(robot.getY() - bullet.getY()) < Bullet.EXPLOSIONG_RANGE + robot.getWidth() / 2
						&& Math.abs(robot.getX() - bullet.getX()) < Bullet.EXPLOSIONG_RANGE + robot.getWidth() / 2) {
					robot.setAlive(false);
					//被击中的机器坦克，删除子弹
					for(String key:resource.getRobotTanks().get(robot.getTankId()).getBullets().keySet()){
						resource.getRobotTanks().get(robot.getTankId()).getBullets().get(key);
						resource.getRobotTanks().get(robot.getTankId()).getBullets().remove(key);
					}
					resource.getRobotTanks().remove(robot.getTankId());
					// 广播击毁信息
					DestroyTankResponse destroyTankResponse = new DestroyTankResponse(robot.getTankId());
					for (Channel channel : TankServer.ROOM_CHANNELGROUP.get(roomId).values()) { // 广播击毁信息
						channel.writeAndFlush(destroyTankResponse);
					}
					if (resource.getRobotTanks().size() == 0 && resource.getPlayerTanks().size() <= 1) { // 如果机器坦克数量为0，并且只有一个玩家坦克，该玩家获胜
						for (Channel channel : TankServer.ROOM_CHANNELGROUP.get(roomId).values()) { // 发送胜利信息
							channel.writeAndFlush(new VictoryResponse());
						}
						// 游戏结束，删除该房间,并且清除该房间的资源
						TankServer.ROOM_CHANNELGROUP.remove(roomId);
						TankServer.RESOURCE.remove(roomId);
						TankServer.isStart.remove(roomId);
						TankServer.MonitorRobotThreads.get(roomId).stopFlag = true; // 停止该房间的寻路线程
						TankServer.MonitorRobotThreads.remove(roomId);
					}
				}
			}
		}
	}

	// 子弹是否击中物体
	public boolean isHit() {
		// 子弹是否击中其他玩家坦克
		for (Tank otherTank : resource.getPlayerTanks().values()) {
			if (tank != otherTank && otherTank.isAlive() == true) { // 如果该坦克还存活
				if (Math.abs(bullet.getX() - otherTank.getX()) <= otherTank.getWidth() / 2
						&& Math.abs(bullet.getY() - otherTank.getY()) <= otherTank.getWidth() / 2) {// 子弹击中坦克
					return true;
				}
			}
		}
		if (!tank.getTankId().startsWith("robot")) { // 如果该子弹不是机器坦克产生的，机器坦克的子弹不能击毁机器坦克
			// 子弹是否击中机器坦克
			for (Tank otherTank : resource.getRobotTanks().values()) {// 子弹是否击中机器坦克
				if (otherTank.isAlive() == true) { // 如果该坦克还存活
					if (Math.abs(bullet.getX() - otherTank.getX()) <= otherTank.getWidth() / 2
							&& Math.abs(bullet.getY() - otherTank.getY()) <= otherTank.getWidth() / 2) {// 子弹击中坦克
						return true;
					}
				}

			}
		}

		for (int l = 0; l < resource.getBricks().size(); l++) { // 取出每个砖块对象与子弹比较
			Brick brick = resource.getBricks().get(l);
			if (Math.abs(bullet.getX() - brick.getX()) <= brick.getWidth() / 2
					&& Math.abs(bullet.getY() - brick.getY()) <= brick.getWidth() / 2) {// 子弹击中砖块
				return true;
			}
		}
		for (int l = 0; l < resource.getIrons().size(); l++) { // 取出每个铁块对象与子弹比较
			Iron iron = resource.getIrons().get(l);
			if (Math.abs(bullet.getX() - iron.getX()) <= iron.getWidth() / 2
					&& Math.abs(bullet.getY() - iron.getY()) <= iron.getWidth() / 2) {// 子弹击中铁块
				return true;
			}
		}
		return false;
	}

	public boolean bulletHit() {

		// 子弹是否击中其他玩家坦克
		for (Tank otherTank : resource.getPlayerTanks().values()) {
			if (tank != otherTank && otherTank.isAlive() == true) { // 如果该坦克还存活
				if (Math.abs(bullet.getX() - otherTank.getX()) <= otherTank.getWidth() / 2
						&& Math.abs(bullet.getY() - otherTank.getY()) <= otherTank.getWidth() / 2) {// 子弹击中坦克
					this.afterShotTank(otherTank);
					return true;
				}
			}
		}

		if (!tank.getTankId().startsWith("robot")) { // 如果该子弹不是机器坦克产生的，机器坦克的子弹不能击毁机器坦克
			// 子弹是否击中机器坦克
			for (Tank otherTank : resource.getRobotTanks().values()) {// 子弹是否击中机器坦克
				if (otherTank.isAlive() == true) { // 如果该坦克还存活
					if (Math.abs(bullet.getX() - otherTank.getX()) <= otherTank.getWidth() / 2
							&& Math.abs(bullet.getY() - otherTank.getY()) <= otherTank.getWidth() / 2) {// 子弹击中坦克
						this.afterShotTank(otherTank);
						return true;
					}
				}

			}
		}

		for (int l = 0; l < resource.getBricks().size(); l++) { // 取出每个砖块对象与子弹比较
			Brick brick = resource.getBricks().get(l);
			if (Math.abs(bullet.getX() - brick.getX()) <= brick.getWidth() / 2
					&& Math.abs(bullet.getY() - brick.getY()) <= brick.getWidth() / 2) {// 子弹击中砖块
				this.afterShotElement(brick);// 击中事物
				return true;
			}
		}
		for (int l = 0; l < resource.getIrons().size(); l++) { // 取出每个铁块对象与子弹比较
			Iron iron = resource.getIrons().get(l);
			if (Math.abs(bullet.getX() - iron.getX()) <= iron.getWidth() / 2
					&& Math.abs(bullet.getY() - iron.getY()) <= iron.getWidth() / 2) {// 子弹击中铁块
				this.afterShotElement(iron); // 击中事物
				return true;
			}
		}
		return false;
	}

	public void afterShotTank(Tank otherTank) {

		otherTank.setAlive(false); // 存活状态设为false，当该坦克发射的子弹消失后，才清除
		if (otherTank.getTankId().startsWith("robot")) { // 删除坦克
			resource.getRobotTanks().remove(otherTank.getTankId());
		} else {
			resource.getPlayerTanks().remove(otherTank.getTankId());
		}
		// 广播击毁信息
		DestroyTankResponse destroyTankResponse = new DestroyTankResponse(otherTank.getTankId());
		for (Channel channel : TankServer.ROOM_CHANNELGROUP.get(roomId).values()) { // 广播子弹信息
			channel.writeAndFlush(destroyTankResponse);
		}
		// 广播爆炸信息
		BombResponse bombResponse = new BombResponse();
		bombResponse.setX(bullet.getX());
		bombResponse.setY(bullet.getY());
		bombResponse.setTankId(tank.getTankId());
		bombResponse.setBulletId(bullet.getBulletId());
		bombResponse.setWidth(40);
		for (Channel channel : TankServer.ROOM_CHANNELGROUP.get(roomId).values()) { // 广播爆炸信息
			channel.writeAndFlush(bombResponse);
		}
		if (TankServer.ROOM_CHANNELGROUP.get(roomId).get(otherTank.getTankId()) != null) { // 如果被击毁的玩家没有关闭游戏，channel仍存在
			TankServer.ROOM_CHANNELGROUP.get(roomId).get(otherTank.getTankId()).writeAndFlush(new FailureResponse());
		}
		TankServer.ROOM_CHANNELGROUP.get(roomId).remove(otherTank.getTankId()); // 将channel移出该房间
		if (resource.getRobotTanks().size() == 0 && resource.getPlayerTanks().size() <= 1) { // 如果机器坦克数量为0，并且只有一个玩家坦克，该玩家获胜
			for (Channel channel : TankServer.ROOM_CHANNELGROUP.get(roomId).values()) { // 发送胜利信息
				channel.writeAndFlush(new VictoryResponse());
			}
			// 游戏结束，删除该房间,并且清除该房间的资源
			TankServer.ROOM_CHANNELGROUP.remove(roomId);
			TankServer.RESOURCE.remove(roomId);
			TankServer.isStart.remove(roomId);
		}
	}

	public void afterShotElement(Element element) {
		BombResponse bombResponse = new BombResponse();
		bombResponse.setX(bullet.getX());
		bombResponse.setY(bullet.getY());
		bombResponse.setTankId(tank.getTankId());
		bombResponse.setBulletId(bullet.getBulletId());
		switch (element.getType()) {
		case Element.BRICK: // 砖块
			CopyOnWriteArrayList<Brick> bricks = TankServer.RESOURCE.get(roomId).getBricks();
			// 删除被击中的砖块
			for (Brick brick : bricks) {
				if (brick.getX() == element.getX() && brick.getY() == element.getY()) {
					bricks.remove(brick);
					break;
				}
			}
			// 广播击中砖块消息
			DestroyBrickResponse destroyBrickResponse = new DestroyBrickResponse();
			destroyBrickResponse.setX(element.getX());
			destroyBrickResponse.setY(element.getY());
			for (Channel channel : TankServer.ROOM_CHANNELGROUP.get(roomId).values()) { // 广播子弹信息
				channel.writeAndFlush(destroyBrickResponse);
			}
			// 设置爆炸宽度信息
			bombResponse.setWidth(40);
			break;
		case Element.IRON: // 铁块
			// 设置爆炸宽度信息
			bombResponse.setWidth(20);
			break;
		}
		// 广播爆炸信息
		for (Channel channel : TankServer.ROOM_CHANNELGROUP.get(roomId).values()) { // 广播子弹信息
			channel.writeAndFlush(bombResponse);
		}
	}

	/**
	 * 欧式距离,小于等于实际值
	 */
	double hEuclidianDistance(int x1, int y1, int x2, int y2) {
		return Math.sqrt(Math.pow(x1 - x2, 2) + Math.pow(y1 - y2, 2));
	}

	public Bullet getBullet() {
		return bullet;
	}

	public void setBullet(Bullet bullet) {
		this.bullet = bullet;
	}

}
