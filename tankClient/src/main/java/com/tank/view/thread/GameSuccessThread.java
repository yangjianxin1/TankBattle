package com.tank.view.thread;

import java.awt.Toolkit;

import javax.swing.JOptionPane;

import com.tank.entity.GameResource;
import com.tank.view.controller.TankController;

public class GameSuccessThread implements Runnable {
	private GameResource resource;

	public GameSuccessThread(GameResource resource) {
		this.resource = resource;
	}

	@Override
	public void run() {

		int y = 0;
		while (true) {
			if (y > 200) {
				break;
			}
			y += 10;
			resource.setGameSuccessY(y);
			try {
				Thread.sleep(36); // 每隔36毫秒移动一次
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		TankController.repaintThread.stop=true;	//结束重绘线程
		Toolkit.getDefaultToolkit().beep();
		JOptionPane.showMessageDialog(TankController.selectModeFrame.getTankPanel(), "游戏胜利，返回大厅", "游戏胜利", JOptionPane.INFORMATION_MESSAGE);
		TankController.roomId=null;
		TankController.selectModeFrame.enterModePanel(); //游戏结束，进入模式选择界面
		TankController.repaintThread.stop=true;
	}

}
