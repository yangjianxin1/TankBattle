package com.tank.view;

import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

public class TankFrame extends JFrame {
	private JMenuBar menuBar;
	private JMenu gameMenu;
	private JMenuItem start;
	private JMenuItem pause;
	private TankPanel tankPanel;	//绘画游戏界面的面板
public static void main(String[] args) {
	new TankFrame();
}
	public TankFrame() {
		
		menuBar = new JMenuBar();
		gameMenu = new JMenu("选项");
		start = new JMenuItem("开始游戏");
		pause = new JMenuItem("暂停游戏");
		tankPanel = new TankPanel();

		start.setActionCommand("start");
		pause.setActionCommand("pause");

		start.addActionListener(tankPanel);
		pause.addActionListener(tankPanel);
		this.addKeyListener(tankPanel);

		gameMenu.add(start);
		gameMenu.add(pause);
		menuBar.add(gameMenu);
		
		this.setJMenuBar(menuBar);
		this.add(tankPanel);
		this.setSize(600, 600);
		this.setTitle("MyTankGame");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false);
		this.setVisible(true);

		// 显示器屏幕大小
		Dimension screenSizeInfo = Toolkit.getDefaultToolkit().getScreenSize();
		int leftTopX = ((int) screenSizeInfo.getWidth() - this.getWidth()) / 2;
		int leftTopY = ((int) screenSizeInfo.getHeight() - this.getHeight()) / 2;
		// 设置显示的位置在屏幕中间
		this.setLocation(leftTopX, leftTopY);
	}

}
