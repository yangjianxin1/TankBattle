package com.tank.service;

import org.springframework.stereotype.Component;

import com.tank.beanPostProcessor.HandlerAnno;
import com.tank.entity.Bomb;
import com.tank.response.BombResponse;
import com.tank.view.TankPanel;
import com.tank.view.controller.TankController;

@Component
public class BombService {
	
	@HandlerAnno
	public void createBomb(BombResponse response) {
		String tankId = response.getTankId();
		String bulletId = response.getBulletId();
		// 删除产生爆炸的子弹
		if (tankId.startsWith("robot")) {
			if (TankPanel.resource.getRobotTanks().get(tankId) == null) {
				return;
			}
			TankPanel.resource.getRobotTanks().get(tankId).getBullets().remove(bulletId);
		} else {
			if (TankPanel.resource.getPlayerTanks().get(tankId) == null) {
				return;
			}
			TankPanel.resource.getPlayerTanks().get(tankId).getBullets().remove(bulletId);
		}
		Bomb bomb = new Bomb(response.getX(), response.getY());
		bomb.setWidth(response.getWidth());
		TankPanel.resource.getBombs().add(bomb);
		TankController.panel.repaint();
	}
}
