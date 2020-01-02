package com.tank.view;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JPanel;

import com.tank.entity.Bullet;
import com.tank.entity.GameResource;
import com.tank.entity.Tank;
import com.tank.request.CreateBulletRequest;
import com.tank.request.SavePogressRequest;
import com.tank.request.TankMoveRequest;
import com.tank.view.controller.TankController;
import com.tank.view.thread.RepaintThread;

public class TankPanel extends JPanel implements KeyListener, ActionListener {
	/**
	 * 游戏面板的宽度
	 */
	public final static int WIDTH = 600;
	/**
	 * 游戏面板的高度
	 */
	public final static int HEIGHT = 600;
	/**
	 * 面板上的资源，坦克...等
	 */
	public static GameResource resource;
	/**
	 * 游戏控制
	 */
	public TankController controller = new TankController(this);
	/**
	 * 绘画类
	 */
	private Draw draw = new Draw();

	public TankPanel() {
		TankController.repaintThread = new RepaintThread(TankController.panel);
		Thread thread = new Thread(TankController.repaintThread);
		thread.start();
	}

	@Override
	public void paint(Graphics g) {
		super.paint(g);
		g.setColor(Color.black);
		g.fillRect(0, 0, WIDTH, HEIGHT);
		// g.fillRect(280, 600, 40, 40);
		if (resource != null) {
			// 画出地图
			if (resource.getMap() != null) {
				draw.drawMap(g, resource.getMap(), this);
			}
			// 画出坦克
			if (resource.getPlayerTanks() != null) {
				draw.drawPlayerTank(g, resource.getPlayerTanks(), this);
			}
			// 画出机器坦克
			if (resource.getRobotTanks() != null) {
				draw.drawRobotTank(g, resource.getRobotTanks(), this);
			}
			if (resource.getBombs() != null) {
				draw.drawBombs(g, resource.getBombs(), this);
			}
			// 画出失败的图片
			if (resource.getGameOverY() != 0) {
				g.drawImage(TankImage.gameOver, 300, resource.getGameOverY() - 10, 200, 200, this);
			}
			// 画出游戏胜利的图片
			if (resource.getGameSuccessY() != 0) {
				g.drawImage(TankImage.win, 300, resource.getGameSuccessY() - 10, 200, 200, this);
			}
		}
	}

	public void actionPerformed(ActionEvent e) {
		switch (e.getActionCommand()) {
		case "start":
			controller.startGame();
			break;
		case "pause":
			TankController.channel.writeAndFlush("pause");
			break;
		}
	}

	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	public void keyPressed(KeyEvent e) {
		Tank myTank = resource.getPlayerTanks().get(TankController.tankId);
		if (myTank != null) {
			if ((e.getKeyCode() == KeyEvent.VK_UP) && myTank.isAlive()) {
				TankMoveRequest request=new TankMoveRequest();
				request.setRoomId(TankController.roomId);
				request.setTankId(TankController.tankId);
				request.setMovingDirect(Tank.NORTH);
				TankController.channel.writeAndFlush(request);
			} else if ((e.getKeyCode() == KeyEvent.VK_DOWN) && myTank.isAlive()) {
				TankMoveRequest request=new TankMoveRequest();
				request.setRoomId(TankController.roomId);
				request.setTankId(TankController.tankId);
				request.setMovingDirect(Tank.SOUTH);
				TankController.channel.writeAndFlush(request);
			} else if ((e.getKeyCode() == KeyEvent.VK_LEFT) && myTank.isAlive() && myTank.getY() <= 580) {
				TankMoveRequest request=new TankMoveRequest();
				request.setRoomId(TankController.roomId);
				request.setTankId(TankController.tankId);
				request.setMovingDirect(Tank.WEST);
				TankController.channel.writeAndFlush(request);
			} else if ((e.getKeyCode() == KeyEvent.VK_RIGHT) && myTank.isAlive() && myTank.getY() <= 580) {
				TankMoveRequest request=new TankMoveRequest();
				request.setRoomId(TankController.roomId);
				request.setTankId(TankController.tankId);
				request.setMovingDirect(Tank.EAST);
				TankController.channel.writeAndFlush(request);
			}
			if (e.getKeyCode() == KeyEvent.VK_X && myTank.isAlive() && myTank.getY() <= 580) {
					if (myTank.getRemainBulletCount()>0) {
						Bullet bullet = myTank.shot(); // 这时才会往容器中添加子弹对象
						CreateBulletRequest request = new CreateBulletRequest(bullet.getX(), bullet.getY(),
								bullet.getMovingDirect(), TankController.tankId, TankController.roomId);
						TankController.channel.writeAndFlush(request);
					}
			}
			if(e.getKeyCode() == KeyEvent.VK_S){	//保存游戏进度
				SavePogressRequest request=new SavePogressRequest();
				request.setTankId(TankController.tankId);
				request.setRoomId(TankController.roomId);
				TankController.channel.writeAndFlush(request);
			}
		}

	}

	public void keyReleased(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_UP) {
			controller.setUp(false);
		} else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
			controller.setDown(false);
		} else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
			controller.setLeft(false);
		} else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
			controller.setRight(false);
		}
	}

	public TankController getController() {
		return controller;
	}

	public void setController(TankController controller) {
		this.controller = controller;
	}

}
