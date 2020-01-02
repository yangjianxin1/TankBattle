package com.tank.view.thread;

import com.tank.entity.Bullet;
import com.tank.entity.Element;
import com.tank.entity.Tank;
import com.tank.view.TankPanel;

public class BulletRepaintThread implements Runnable {
	private Bullet bullet;
	private Tank tank;

	public BulletRepaintThread(Bullet bullet,Tank tank) {
		this.tank=tank;
		this.bullet = bullet;
	}

	@Override
	public void run() {

		while (true) {
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
			if (bullet.getX() < 5 || bullet.getX() > TankPanel.WIDTH - 5 || bullet.getY() < 5
					|| bullet.getY() > TankPanel.HEIGHT - 5) { // 判断子弹是否碰到边界
				bullet.setAlive(false); // 子弹死亡
				break;
			}
			try {
				Thread.sleep(30); // 每隔30毫秒移动一次
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		synchronized (this.tank.getRemainBulletCount()) {
			this.tank.increseRemainBulletCount();
		}

	}

	public Bullet getBullet() {
		return bullet;
	}

	public void setBullet(Bullet bullet) {
		this.bullet = bullet;
	}

}
