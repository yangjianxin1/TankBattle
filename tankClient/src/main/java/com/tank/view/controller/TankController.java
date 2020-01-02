package com.tank.view.controller;

import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

import com.tank.entity.Bomb;
import com.tank.entity.Brick;
import com.tank.entity.Bullet;
import com.tank.entity.Element;
import com.tank.entity.GameResource;
import com.tank.entity.Iron;
import com.tank.entity.RobotTank;
import com.tank.entity.Tank;
import com.tank.entity.Water;
import com.tank.map.Map;
import com.tank.request.DestroyBrickRequest;
import com.tank.request.DestroyTankRequest;
import com.tank.request.StartGameRequest;
import com.tank.view.LoginFrame;
import com.tank.view.SelectModeFrame;
import com.tank.view.TankPanel;
import com.tank.view.thread.RepaintRobotThread;
import com.tank.view.thread.RepaintThread;

import io.netty.channel.Channel;

/**
 * 重绘的控制类
 * 
 * @author young
 *
 */
public class TankController {
	/**
	 * 是否按了向上的方向键
	 */
	private boolean up = false;
	/**
	 * 是否按了向下的方向键
	 */
	private boolean down = false;
	/**
	 * 是否按了向左的方向键
	 */
	private boolean left = false;
	/**
	 * 是否按了向右的方向键
	 */
	private boolean right = false;
	// 与服务端通信的channel
	public static Channel channel;
	// tankId
	public static String tankId; // 用户id
	public static String password;
	public static String roomId; // 房间id
	public static TankPanel panel;
	// public static boolean isSuccess; // 是否成功的标识
	public static LoginFrame loginFrame;
	public static SelectModeFrame selectModeFrame;
	public static RepaintThread repaintThread;

	public TankController(TankPanel panel) {
		this.panel = panel;
	}

	/**
	 * 开始游戏
	 */
	public void startGame() {
		TankController.channel.writeAndFlush(new StartGameRequest(TankController.roomId)); // 请求地图信息
	}

	/**
	 * 子弹是否击中物体
	 * 
	 * @param ressource
	 */
	public void bulletHit(GameResource resource) {
		Map map = resource.getMap();
		CopyOnWriteArrayList<Bomb> bombs = resource.getBombs();
		for (Tank tank : resource.getPlayerTanks().values()) { // 判断每个坦克是否击中其他物体

			for (Bullet bullet : tank.getBullets().values()) {

				// 子弹是否击中其他玩家坦克
				for (Tank otherTank : resource.getPlayerTanks().values()) {
					if (tank != otherTank && otherTank.isAlive() == true) { // 如果该坦克还存活
						if (Math.abs(bullet.getX() - otherTank.getX()) <= otherTank.getWidth() / 2
								&& Math.abs(bullet.getY() - otherTank.getY()) <= otherTank.getWidth() / 2) {// 子弹击中坦克
							// this.afterShotStuff(bullet, otherTank, bombs,
							// tank);// 击中事物
							this.afterShotTank(tank, resource, bullet, otherTank, bombs, resource.getPlayerTanks());
						}
					}
				}

				// 子弹是否击中机器坦克
				for (Tank otherTank : resource.getRobotTanks().values()) {// 子弹是否击中机器坦克
					if (otherTank.isAlive() == true) { // 如果该坦克还存活
						if (Math.abs(bullet.getX() - otherTank.getX()) <= otherTank.getWidth() / 2
								&& Math.abs(bullet.getY() - otherTank.getY()) <= otherTank.getWidth() / 2) {// 子弹击中坦克
							// this.afterShotStuff(bullet, otherTank, bombs,
							// tank);// 击中事物
							this.afterShotTank(tank, resource, bullet, otherTank, bombs, resource.getPlayerTanks());
						}
					}
				}

				for (int l = 0; l < map.getBricks().size(); l++) { // 取出每个砖块对象与子弹比较
					Brick brick = map.getBricks().get(l);
					if (Math.abs(bullet.getX() - brick.getX()) <= brick.getWidth() / 2
							&& Math.abs(bullet.getY() - brick.getY()) <= brick.getWidth() / 2) {// 子弹击中砖块
						this.afterShotStuff(bullet, brick, bombs, tank);// 击中事物
					}
				}
				for (int l = 0; l < map.getIrons().size(); l++) { // 取出每个铁块对象与子弹比较
					Iron iron = map.getIrons().get(l);
					if (Math.abs(bullet.getX() - iron.getX()) <= iron.getWidth() / 2
							&& Math.abs(bullet.getY() - iron.getY()) <= iron.getWidth() / 2) {// 子弹击中铁块
						this.afterShotStuff(bullet, iron, bombs, tank); // 击中事物
					}
				}
			}
		}

		// 机器坦克是否击中其他物体
		for (RobotTank tank : resource.getRobotTanks().values()) {
			for (Bullet bullet : tank.getBullets().values()) {

				for (Tank otherTank : resource.getPlayerTanks().values()) {// 子弹是否击中其他坦克
					if (tank != otherTank && otherTank.isAlive() == true) { // 如果该坦克还存活
						if (Math.abs(bullet.getX() - otherTank.getX()) <= otherTank.getWidth() / 2
								&& Math.abs(bullet.getY() - otherTank.getY()) <= otherTank.getWidth() / 2) {// 子弹击中坦克
							// this.afterShotStuff(bullet, otherTank, bombs,
							// tank);// 击中事物
							this.afterShotTank(tank, resource, bullet, otherTank, bombs, resource.getPlayerTanks());
						}
					}
				}

				for (int l = 0; l < map.getBricks().size(); l++) { // 取出每个砖块对象与子弹比较
					Brick brick = map.getBricks().get(l);
					if (Math.abs(bullet.getX() - brick.getX()) <= brick.getWidth() / 2
							&& Math.abs(bullet.getY() - brick.getY()) <= brick.getWidth() / 2) {// 子弹击中砖块
						this.afterShotStuff(bullet, brick, bombs, tank);// 击中事物
					}
				}
				for (int l = 0; l < map.getIrons().size(); l++) { // 取出每个铁块对象与子弹比较
					Iron iron = map.getIrons().get(l);
					if (Math.abs(bullet.getX() - iron.getX()) <= iron.getWidth() / 2
							&& Math.abs(bullet.getY() - iron.getY()) <= iron.getWidth() / 2) {// 子弹击中铁块
						this.afterShotStuff(bullet, iron, bombs, tank); // 击中事物
					}
				}
			}
		}

	}

	/**
	 * 击中地图上的东西以后
	 */
	public void afterShotStuff(Bullet bullet, Element element, CopyOnWriteArrayList<Bomb> bombs, Tank tank) {

		Bomb bomb;
		switch (element.getType()) {
		case Element.BRICK: // 砖块
			bullet.setAlive(false);
			element.setAlive(false);
			bomb = new Bomb(element.getX(), element.getY());
			bomb.setWidth(40);
			bombs.add(bomb);
			if (tank.getTankId().equals(TankController.tankId)) {
				DestroyBrickRequest reuqest = new DestroyBrickRequest();
				reuqest.setX(element.getX());
				reuqest.setY(element.getY());
				reuqest.setRoomId(TankController.roomId);
				TankController.channel.writeAndFlush(reuqest);
			}
			break;
		case Element.IRON: // 铁块
			bomb = new Bomb(bullet.getX(), bullet.getY());
			bullet.setAlive(false);
			bomb.setWidth(20);
			bombs.add(bomb);
			break;
		// case Element.TANK:
		// bullet.setAlive(false);
		// element.setAlive(false);
		// bomb = new Bomb(element.getX(), element.getY());
		// bomb.setWidth(40);
		// bombs.add(bomb);
		// break;
		}
	}

	/**
	 * 子弹击中坦克
	 * 
	 * @param bullet
	 * @param otherTank
	 * @param bombs
	 * @param playerTanks
	 */
	public void afterShotTank(Tank tank, GameResource gameResource, Bullet bullet, Tank otherTank,
			CopyOnWriteArrayList<Bomb> bombs, ConcurrentHashMap<String, Tank> playerTanks) {
		bullet.setAlive(false);
		otherTank.setAlive(false);
		Bomb bomb = new Bomb(otherTank.getX(), otherTank.getY());
		bomb.setWidth(40);
		bombs.add(bomb);
		if (tank.getTankId().equals(TankController.tankId)) { // 我的坦克击中别的坦克
			TankController.channel.writeAndFlush(new DestroyTankRequest(otherTank.getTankId(), TankController.roomId));
		} else if (tank.getTankId().startsWith("robot")) { // 机器坦克击中别的坦克
			TankController.channel.writeAndFlush(new DestroyTankRequest(otherTank.getTankId(), TankController.roomId));
		}
	}

	/**
	 * 清除所有死亡的对象
	 */
	public void clearDieElement(GameResource resource, CopyOnWriteArrayList<Bomb> bombs,
			ConcurrentHashMap<String, RobotTank> robotTanks, ConcurrentHashMap<String, Tank> playerTanks, Map map) {
		// 处理电脑坦克
		for (RobotTank tank : robotTanks.values()) {
			HashMap<String, Bullet> bullets = tank.getBullets();
//			ConcurrentHashMap<String, Bullet> bullets = tank.getBullets();;
			// 清除电脑坦克产生的死亡的子弹
			for (Bullet bullet : bullets.values()) {
				if (bullet.isAlive() == false) {
					bullets.remove(bullet.getBulletId());
				}
			}
			// 清除死亡的电脑坦克
			if (tank.isAlive() == false && tank.getBullets().size() == 0) { // 如果坦克已死亡，并且地图上没有该坦克产生的子弹，移除该坦克
				playerTanks.remove(tank.getTankId());
			}
		}

		// 清除消失的爆炸图像
		for (Bomb bomb : bombs) {
			if (bomb.isAlive() == false) {
				bombs.remove(bomb);
			}
		}

		// 清除消失的砖块
		for (Brick brick : map.getBricks()) {
			if (brick.isAlive() == false) {
				map.getBricks().remove(brick);
			}
		}

		// 处理坦克
		for (Tank tank : playerTanks.values()) {
			for (Bullet bullet : tank.getBullets().values()) {
				if (bullet.isAlive() == false) {
					tank.getBullets().remove(bullet);
				}
			}

			if (tank.isAlive() == false && tank.getBullets().size() == 0) { // 如果坦克已死亡，并且地图上没有该坦克产生的子弹，移除该坦克
				playerTanks.remove(tank.getTankId());
			}
		}

	}

	/**
	 * 判断我的坦克是否与地形碰撞
	 * 
	 * @param myTank
	 * @param map
	 */
	public void judgeCrash(Tank myTank, Map map) {
		if (myTank == null)
			return;
		myTank.setCrash(false);
		CopyOnWriteArrayList<Brick> bricks = map.getBricks();
		CopyOnWriteArrayList<Water> waters = map.getWaters();
		CopyOnWriteArrayList<Iron> irons = map.getIrons();
		for (Tank tank : TankPanel.resource.getPlayerTanks().values()) { // 判断我的坦克是否与其他坦克重叠
			if (tank != myTank) {
				if (myTank.Overlap(tank) && tank.isAlive()) {
					myTank.setCrash(true);
					break;
				}
			}
		}
		for (RobotTank robot : TankPanel.resource.getRobotTanks().values()) { // 判断我的坦克是否与机器坦克碰撞
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
	}

	/**
	 * 我的坦克事件，观察我按了什么键
	 * 
	 * @param resource
	 */
	public void myTankEvent(GameResource resource) {
		Tank myTank = resource.getPlayerTanks().get(TankController.tankId);
		if (myTank == null)
			return;
		// 坦克没有与其他物体发生碰撞，就往对应方向移动
		if (up == true && myTank.isCrash() == false) {
			myTank.goNorth();
		} else if (down == true && myTank.isCrash() == false) {
			myTank.goSouth(TankPanel.HEIGHT);
		} else if (left == true && myTank.isCrash() == false) {
			myTank.goWest(TankPanel.HEIGHT);
		} else if (right == true && myTank.isCrash() == false) {
			myTank.goEast(TankPanel.HEIGHT, TankPanel.WIDTH);
		}
	}

	public boolean isUp() {
		return up;
	}

	public void setUp(boolean up) {
		this.up = up;
	}

	public boolean isDown() {
		return down;
	}

	public void setDown(boolean down) {
		this.down = down;
	}

	public boolean isLeft() {
		return left;
	}

	public void setLeft(boolean left) {
		this.left = left;
	}

	public boolean isRight() {
		return right;
	}

	public void setRight(boolean right) {
		this.right = right;
	}

}
