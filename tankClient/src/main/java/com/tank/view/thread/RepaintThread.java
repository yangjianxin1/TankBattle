package com.tank.view.thread;

import com.tank.entity.RobotTank;
import com.tank.entity.Tank;
import com.tank.request.TankLocationRequest;
import com.tank.view.TankPanel;
import com.tank.view.controller.TankController;

/**
 * 重画线程
 * 
 * @author young
 *
 */
public class RepaintThread implements Runnable {
	/**
	 * 待重画的界面
	 */
	private TankPanel tankPanel;
	public boolean stop = false;

	public RepaintThread(TankPanel tankPanel) {
		this.tankPanel = tankPanel;
	}

	public void run() {
		System.out.println("start RepaintThread!!!");
		TankController controller = tankPanel.getController();
		while (true) {
			if (stop == true) {
				break;
			}
			try {
				Thread.sleep(30);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			if (TankPanel.resource != null) {
				// 移除消失对象
//				controller.clearDieElement(TankPanel.resource, TankPanel.resource.getBombs(),
//						TankPanel.resource.getRobotTanks(), TankPanel.resource.getPlayerTanks(),
//						TankPanel.resource.getMap());
				// 处理坦克是否与其他物体碰撞
//				controller.judgeCrash(TankPanel.resource.getPlayerTanks().get(TankController.tankId),
//						TankPanel.resource.getMap());
				// 坦克移动
//				controller.myTankEvent(TankPanel.resource);
				// 坦克子弹是否打中物体
//				controller.bulletHit(TankPanel.resource);
				// 将我的坦克的位置信息发送给服务端
//				Tank tank = TankPanel.resource.getPlayerTanks().get(TankController.tankId);
//				if (tank != null) {
//					TankController.channel.writeAndFlush(new TankLocationRequest(tank.getX(), tank.getY(),
//							tank.getTankId(), tank.getDirect(), TankController.roomId));
//				}
//				//将机器坦克的位置信息发送给服务端
//				String robotName=TankPanel.resource.getRobotTanks().keys().nextElement();
//				RobotTank robot=TankPanel.resource.getRobotTanks().get(robotName);
//				if(robot!=null){
//					TankController.channel.writeAndFlush(new TankLocationRequest(robot.getX(), robot.getY(),
//							robot.getTankId(), robot.getDirect(), TankController.roomId));
//				}
			}
			tankPanel.repaint();
		}

	}

}
