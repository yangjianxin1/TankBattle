package com.tank.service;

import java.util.concurrent.CopyOnWriteArrayList;

import com.tank.entity.Brick;
import com.tank.server.handler.TankServer;

public class BrickService {

	/**
	 * 删除对应的砖块
	 * @param roomId
	 * @param x
	 * @param y
	 */
	public void removeBrick(String roomId, int x, int y) {
		CopyOnWriteArrayList<Brick> bricks = TankServer.RESOURCE.get(roomId).getBricks();
		for (Brick brick : bricks) {
			if (brick.getX() == x && brick.getY() == y) {
				bricks.remove(brick);
				break;
			}
		}
	}

}
