package com.tank.service;

import java.util.concurrent.CopyOnWriteArrayList;

import org.springframework.stereotype.Component;

import com.tank.beanPostProcessor.HandlerAnno;
import com.tank.entity.Brick;
import com.tank.entity.Tank;
import com.tank.response.DestroyBrickResponse;
import com.tank.response.DestroyTankResponse;
import com.tank.response.TankLocationResponse;
import com.tank.view.TankPanel;
import com.tank.view.controller.TankController;
@Component
public class TankService {

	/**
	 * 刷新坦克的位置
	 * 
	 * @param response
	 */
	@HandlerAnno
	public void refreshTankLocation(TankLocationResponse response) {
		Tank tank;
		if (response.getTankId().startsWith("robot")) {
			tank = TankPanel.resource.getRobotTanks().get(response.getTankId());
		} else {
			tank = TankPanel.resource.getPlayerTanks().get(response.getTankId());
		}
		if (tank != null) {
			tank.setX(response.getX());
			tank.setY(response.getY());
			tank.setDirect(response.getDirect());
		}
		TankController.panel.repaint();
	}

	/**
	 * 坦克被击毁
	 * 
	 * @param response
	 */
	@HandlerAnno
	public void destroyTank(DestroyTankResponse response) {
		String tankId = response.getTankId();
		if (tankId.startsWith("robot")) {
			TankPanel.resource.getRobotTanks().remove(tankId);
		} else {
			TankPanel.resource.getPlayerTanks().remove(tankId);
		}
	}
}
