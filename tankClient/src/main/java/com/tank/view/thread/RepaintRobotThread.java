package com.tank.view.thread;

import java.util.LinkedList;
import java.util.concurrent.ConcurrentHashMap;

import com.tank.entity.Bullet;
import com.tank.entity.Iron;
import com.tank.entity.RobotTank;
import com.tank.entity.Tank;
import com.tank.request.RobotRouteRequest;
import com.tank.request.TankLocationRequest;
import com.tank.response.RobotRouteResponse;
import com.tank.view.TankPanel;
import com.tank.view.controller.TankController;

/**
 * 重画机器坦克的线程
 * 
 * @author Administrator
 *
 */
public class RepaintRobotThread implements Runnable {
	private LinkedList<Tank> route;
	private String tankId;
	private TankPanel tankPanel;
	private boolean flag;
	private boolean read; // 路径消息是否已经读过

	public RepaintRobotThread(LinkedList<Tank> route, String tankId, TankPanel tankPanel) {
		this.route = route;
		this.tankId = tankId;
		this.tankPanel = tankPanel;
	}

	@Override
	public void run() {

		ConcurrentHashMap<String, RobotTank> robots = TankPanel.resource.getRobotTanks();
		RobotTank robot;
		robot = robots.get(tankId);
		try {
			Thread.sleep(30);
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
		// 遍历路径
		for (Tank tank : route) {
			try {
				Thread.sleep(30);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			// System.out.println("robot
			// location:"+robot.getX()+","+robot.getY());
			// System.out.println("next step:"+tank.getX()+","+tank.getY());
			// 服务端发过来的规划路线的的第一个位置，是机器坦克的初始位置信息，不需要
			if (tank.getX().intValue() == robot.getX().intValue()
					&& tank.getY().intValue() == robot.getY().intValue()) { // 收到的第一条
				continue;
			}
			for (Tank t : TankPanel.resource.getPlayerTanks().values()) { // 判断机器坦克是否与其他玩家坦克重叠,并且发射子弹能击中终点坦克
//				if (tank.Overlap(t)&&(Math.abs(t.getX() - robot.getX()) < robot.getWidth() / 2||Math.abs(t.getY() - robot.getY()) < robot.getWidth() / 2)) {
//					tank.setCrash(true);
//					break;
//				}
			}
			for (Tank t : TankPanel.resource.getRobotTanks().values()) { // 判断机器坦克是否与其他机器坦克重叠
				if (!tank.getTankId().equals(t.getTankId())) {
					if (tank.Overlap(t)&&t.isAlive()) {	//有可能坦克死了，但是子弹仍然显示在界面上，坦克未被移除
						tank.setCrash(true);
						break;
					}
				}
			}
			if (tank.isCrash() == true) { // 在路径上碰撞的话，不再继续走
				break;
			}
			robot.setX(tank.getX());
			robot.setY(tank.getY());
			robot.setDirect(tank.getDirect());
			// 如果在行进方向上，没有钢铁，并且开炮后，炮弹的行进轨迹与坦克所在的x轴或y轴偏差小于坦克宽度的一半
			for (Tank t : TankPanel.resource.getPlayerTanks().values()) {
				if (Math.abs(t.getX() - robot.getX()) < robot.getWidth() / 2 && t.getY() < robot.getY()) { // 如果机器坦克在下面
					boolean ironFlag = false; // 路径中是否有钢铁的标识
					for (Iron iron : TankPanel.resource.getMap().getIrons()) { // 子弹路径上，不会有钢铁
						if (Math.abs(iron.getX() - robot.getX()) < iron.getWidth() / 2 && iron.getY() < robot.getY()
								&& iron.getY() > t.getY()) {
							ironFlag = true;
							break;
						}
					}
					if (ironFlag == false) {
						robot.setDirect(Tank.NORTH);
						synchronized (robot.getRemainBulletCount()) {
							if (robot.getRemainBulletCount()>0) {
//								Bullet bullet = robot.shot();
//								Thread thread = new Thread(new BulletRepaintThread(bullet, robot));
//								thread.start();
							}
						}
						
					}
				} else if (Math.abs(t.getX() - robot.getX()) < robot.getWidth() / 2 && t.getY() > robot.getY()) { // 如果机器坦克在上面
					boolean ironFlag = false; // 路径中是否有钢铁的标识
					for (Iron iron : TankPanel.resource.getMap().getIrons()) { // 子弹路径上，不会有钢铁
						if (Math.abs(iron.getX() - robot.getX()) < iron.getWidth() / 2 && iron.getY() > robot.getY()
								&& iron.getY() < t.getY()) {
							ironFlag = true;
							break;
						}
					}
					if (ironFlag == false) {
						robot.setDirect(Tank.SOUTH);
						synchronized (robot.getRemainBulletCount()) {
							if (robot.getRemainBulletCount()>0) {
//								Bullet bullet = robot.shot();
//								Thread thread = new Thread(new BulletRepaintThread(bullet, robot));
//								thread.start();
							}
						}
					}
				} else if (Math.abs(t.getY() - robot.getY()) < robot.getWidth() / 2 && t.getX() > robot.getX()) { // 如果机器坦克在左边
					boolean ironFlag = false; // 路径中是否有钢铁的标识
					for (Iron iron : TankPanel.resource.getMap().getIrons()) { // 子弹路径上，不会有钢铁
						if (Math.abs(iron.getY() - robot.getY()) < iron.getWidth() / 2 && iron.getX() > robot.getX()
								&& iron.getX() < t.getX()) {
							ironFlag = true;
							break;
						}
					}
					if (ironFlag == false) {
						robot.setDirect(Tank.EAST);
						synchronized (robot.getRemainBulletCount()) {
							if (robot.getRemainBulletCount()>0) {
//								Bullet bullet = robot.shot();
//								Thread thread = new Thread(new BulletRepaintThread(bullet, robot));
//								thread.start();
							}
						}
					}
				} else if (Math.abs(t.getY() - robot.getY()) < robot.getWidth() / 2 && t.getX() < robot.getX()) { // 如果机器坦克在右边
					boolean ironFlag = false; // 路径中是否有钢铁的标识
					for (Iron iron : TankPanel.resource.getMap().getIrons()) { // 子弹路径上，不会有钢铁
						if (Math.abs(iron.getY() - robot.getY()) < iron.getWidth() / 2 && iron.getX() < robot.getX()
								&& iron.getX() > t.getX()) {
							ironFlag = true;
							break;
						}
					}
					if (ironFlag == false) {
						robot.setDirect(Tank.WEST);
						synchronized (robot.getRemainBulletCount()) {
							if (robot.getRemainBulletCount()>0) {
//								Bullet bullet = robot.shot();
//								Thread thread = new Thread(new BulletRepaintThread(bullet, robot));
//								thread.start();
							}
						}
					}
				}
			}
			TankController.panel.repaint();
		}

		if (robot != null) {
			TankController.channel.writeAndFlush(new TankLocationRequest(robot.getX(), robot.getY(), robot.getTankId(),
					robot.getDirect(), TankController.roomId));
		}

		RobotRouteRequest request = new RobotRouteRequest();
		request.setFromTankId(robot.getTankId());
		request.setMapHeight(TankPanel.HEIGHT);
		request.setMapWidth(TankPanel.WIDTH);
		request.setRoomId(TankController.roomId);
		request.setSpeed(4);
		request.setTankWidth(20);
		request.setToTankId(TankController.tankId);
		TankController.channel.writeAndFlush(request);

	}

	public LinkedList<Tank> getRoute() {
		return route;
	}

	public void setRoute(LinkedList<Tank> route) {
		this.route = route;
	}

	public String getTankId() {
		return tankId;
	}

	public void setTankId(String tankId) {
		this.tankId = tankId;
	}

	public TankPanel getTankPanel() {
		return tankPanel;
	}

	public void setTankPanel(TankPanel tankPanel) {
		this.tankPanel = tankPanel;
	}

	public boolean isFlag() {
		return flag;
	}

	public void setFlag(boolean flag) {
		this.flag = flag;
	}

	public boolean isRead() {
		return read;
	}

	public void setRead(boolean read) {
		this.read = read;
	}

}
