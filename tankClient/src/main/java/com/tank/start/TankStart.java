package com.tank.start;

import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JFrame;

import com.tank.client.handler.NettyClient;
import com.tank.view.TankFrame;

public class TankStart {
	public static void main(String[] args) {
		//开启新线程，连接服务器
		new Thread(new Runnable() {
			public void run() {
				try {
					new NettyClient().connect();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}).start();
	}
}
