package com.tank.service;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

import org.springframework.stereotype.Component;

import com.tank.beanPostProcessor.HandlerAnno;
import com.tank.entity.Brick;
import com.tank.entity.GameResource;
import com.tank.entity.Iron;
import com.tank.entity.RobotTank;
import com.tank.entity.Tank;
import com.tank.entity.Water;
import com.tank.map.Map;
import com.tank.response.DestroyBrickResponse;
import com.tank.response.InitialMapResponse;
import com.tank.view.TankPanel;
import com.tank.view.controller.TankController;

@Component
public class MapService {

	/**
	 * 初始化地图
	 * 
	 * @param response
	 */
	@HandlerAnno
	public void initMap(InitialMapResponse response) {
		GameResource resource = new GameResource();
		CopyOnWriteArrayList<Brick> bricks = new CopyOnWriteArrayList<>(response.getBricks());
		CopyOnWriteArrayList<Water> waters = new CopyOnWriteArrayList<>(response.getWaters());
		CopyOnWriteArrayList<Iron> irons = new CopyOnWriteArrayList<>(response.getIrons());
		ConcurrentHashMap<String, Tank> playerTanks = new ConcurrentHashMap<>(response.getPlayerTanks());
		ConcurrentHashMap<String, RobotTank> robots = new ConcurrentHashMap<>(response.getRobots());
		Map map = new Map();
		map.setBricks(bricks);
		map.setWaters(waters);
		map.setIrons(irons);
		resource.setMap(map);
		resource.setPlayerTanks(playerTanks);
		resource.setRobotTanks(robots);
		TankPanel.resource = resource;
		if (response.getRoomId() != null && !response.getRoomId().trim().equals("")) {
			TankController.roomId = response.getRoomId();
		}
		TankController.selectModeFrame.enterGame(); // 切换至游戏面板
		switch (response.getGameMode()) {
		case PVP:
			break;
		case PVC:
			break;
		}

	}

	/**
	 * 删除被击毁的砖块
	 * 
	 * @param response
	 */
	@HandlerAnno
	public void removeBrick(DestroyBrickResponse response) {
		CopyOnWriteArrayList<Brick> bricks = TankPanel.resource.getMap().getBricks();
		for (Brick brick : bricks) {
			if (brick.getX() == response.getX() && brick.getY() == response.getY()) {
				bricks.remove(brick);
			}
		}

	}
}
