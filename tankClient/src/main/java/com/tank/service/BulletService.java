package com.tank.service;

import java.util.HashMap;

import org.springframework.stereotype.Component;

import com.tank.beanPostProcessor.HandlerAnno;
import com.tank.entity.Bullet;
import com.tank.response.BulletLocationResponse;
import com.tank.view.TankPanel;
@Component
public class BulletService {
	/**
	 * 刷新子弹位置
	 * 
	 * @param response
	 */
	@HandlerAnno
	public void refreshBulletLocation(BulletLocationResponse response) {
		String bulletId = response.getBulletId();
		String tankId = response.getTankId();
		int x = response.getX();
		int y = response.getY();
		Bullet bullet;
		HashMap<String, Bullet> bullets;
		if (tankId.startsWith("robot")) { // 如果是机器坦克的子弹重绘
			if (TankPanel.resource.getRobotTanks().get(tankId) == null) { // 如果坦克已被击毁
				return;
			}
			bullets = TankPanel.resource.getRobotTanks().get(tankId).getBullets();
			bullet = bullets.get(bulletId);

		} else {
			if (TankPanel.resource.getPlayerTanks().get(tankId) == null) { // 如果坦克已被击毁
				return;
			}
			bullets = TankPanel.resource.getPlayerTanks().get(tankId).getBullets();
			bullet = TankPanel.resource.getPlayerTanks().get(tankId).getBullets().get(bulletId);
		}
		
		bullet = bullets.get(bulletId);
		if (bullet == null) {
			bullet = new Bullet(x, y, -1);
			bullets.put(bulletId, bullet);
		}
		bullet.setX(x);
		bullet.setY(y);
		if (x < 5 || x > 600 - 5 || y < 5 || y > 600 - 5) { // 判断子弹是否碰到边界
			bullets.remove(bulletId);
		}

	}
}
