package com.tank.view;

import java.awt.Image;
import java.awt.Panel;
import java.awt.Toolkit;

public class TankImage {
	/**
	 * 我的坦克四个方向图像数组
	 */
	public static Image myTankImg[] = {
			Toolkit.getDefaultToolkit().getImage(Panel.class.getResource("/images/UTank_.gif")),
			Toolkit.getDefaultToolkit().getImage(Panel.class.getResource("/images/DTank_.gif")),
			Toolkit.getDefaultToolkit().getImage(Panel.class.getResource("/images/LTank_.gif")),
			Toolkit.getDefaultToolkit().getImage(Panel.class.getResource("/images/RTank_.gif")) };
	
	/**
	 * 敌方坦克四个方向图像数组
	 */
	public static Image enemyTankImg[] = {
			Toolkit.getDefaultToolkit().getImage(Panel.class.getResource("/images/UTank.gif")),
			Toolkit.getDefaultToolkit().getImage(Panel.class.getResource("/images/DTank.gif")),
			Toolkit.getDefaultToolkit().getImage(Panel.class.getResource("/images/LTank.gif")),
			Toolkit.getDefaultToolkit().getImage(Panel.class.getResource("/images/RTank.gif")) };

	/**
	 * 障碍物图像数组
	 */
	public static Image stuffImg[] = {
			Toolkit.getDefaultToolkit().getImage(Panel.class.getResource("/images/brick.gif")),
			Toolkit.getDefaultToolkit().getImage(Panel.class.getResource("/images/iron.gif")),
			Toolkit.getDefaultToolkit().getImage(Panel.class.getResource("/images/water.gif")) };
	/**
	 * 子弹图像
	 */
	public static Image bullet = Toolkit.getDefaultToolkit().getImage(Panel.class.getResource("/images/bullet.gif"));
	
	/**
	 * 游戏失败图像
	 */
	public static Image gameOver = Toolkit.getDefaultToolkit().getImage(Panel.class.getResource("/images/gameOver.jpg"));
	
	/**
	 * 游戏胜利图像
	 */
	public static Image win = Toolkit.getDefaultToolkit().getImage(Panel.class.getResource("/images/win.jpg"));

	
	/**
	 * 爆炸图像
	 */
	public static Image bomb[] = { Toolkit.getDefaultToolkit().getImage(Panel.class.getResource("/images/bomb_1.png")),
			Toolkit.getDefaultToolkit().getImage(Panel.class.getResource("/images/bomb_2.png")),
			Toolkit.getDefaultToolkit().getImage(Panel.class.getResource("/images/bomb_3.png")),
			Toolkit.getDefaultToolkit().getImage(Panel.class.getResource("/images/bomb_4.png")),
			Toolkit.getDefaultToolkit().getImage(Panel.class.getResource("/images/bomb_5.png")), };
}
