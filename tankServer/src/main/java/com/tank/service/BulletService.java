package com.tank.service;

import com.tank.entity.Bullet;
import com.tank.entity.Tank;
import com.tank.server.handler.TankServer;
import com.tank.thread.BulletRepaintThread;

public class BulletService {

	/**
	 * 创建子弹
	 * 
	 * @param tankId
	 * @param roomId
	 */
	public void createBullet(String tankId, String roomId) {
		if (roomId != null && tankId != null && TankServer.RESOURCE.get(roomId) != null) { // 收到该消息，游戏可能已经结束
			//如果子弹个数小于等于0，不发射子弹
			if (TankServer.RESOURCE.get(roomId).getPlayerTanks().get(tankId).getRemainBulletCount() <= 0) {
				return;
			}
			Tank tank = TankServer.RESOURCE.get(roomId).getPlayerTanks().get(tankId);
			Bullet bullet = tank.shot();
			Thread t = new Thread(new BulletRepaintThread(bullet, tank, roomId));
			t.start();
		}
	}
}
