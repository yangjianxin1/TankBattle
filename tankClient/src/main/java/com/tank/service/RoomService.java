package com.tank.service;

import java.awt.Toolkit;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import org.springframework.stereotype.Component;

import com.tank.beanPostProcessor.HandlerAnno;
import com.tank.response.CreateRoomResponse;
import com.tank.response.RoomResponse;
import com.tank.view.controller.TankController;
@Component
public class RoomService {

	/**
	 * 处理进入房间的响应
	 * 
	 * @param response
	 */
	@HandlerAnno
	public void handleEnterRoomResponse(RoomResponse response) {
		switch (response.getRoomState()) {
		case RoomNoExist:
			Toolkit.getDefaultToolkit().beep();
			JOptionPane.showMessageDialog(TankController.selectModeFrame.getCreateRoomPanel(), "该房间号不存在", "进入房间错误",
					JOptionPane.ERROR_MESSAGE);
			break;
		case RoomExist:
			TankController.roomId = response.getRoomId();
			TankController.selectModeFrame.updateRoomPanel(response.getPlayerIds(), response.getRoomId());
			break;
		case GameIsSatrted:
			Toolkit.getDefaultToolkit().beep();
			JOptionPane.showMessageDialog(TankController.selectModeFrame.getRoomPanel(), "该房间游戏已开始", "游戏已开始",
					JOptionPane.ERROR_MESSAGE);
			break;
		}

	}

	/**
	 * 设置当前房间号
	 * @param response
	 */
	@HandlerAnno
	public void setRoomId(CreateRoomResponse response) {
		TankController.roomId = response.getRoomId();
		List<String> ids = new ArrayList<>();
		ids.add(TankController.tankId);
		TankController.selectModeFrame.updateRoomPanel(ids, response.getRoomId());

	}
}
