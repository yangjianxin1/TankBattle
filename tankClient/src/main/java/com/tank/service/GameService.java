package com.tank.service;

import java.awt.Toolkit;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.swing.JOptionPane;

import org.springframework.stereotype.Component;

import com.tank.beanPostProcessor.HandlerAnno;
import com.tank.request.StartGameRequest;
import com.tank.response.ChapterResponse;
import com.tank.response.FailureResponse;
import com.tank.response.JudgeStorageResponse;
import com.tank.response.LoginResponse;
import com.tank.response.ReConnectResponse;
import com.tank.response.RegisterResponse;
import com.tank.response.RejectStartGameResponse;
import com.tank.response.SaveProgressResponse;
import com.tank.response.VictoryResponse;
import com.tank.view.SelectModeFrame;
import com.tank.view.TankPanel;
import com.tank.view.controller.TankController;
import com.tank.view.thread.GameOverThread;
import com.tank.view.thread.GameSuccessThread;

@Component
public class GameService {
	@HandlerAnno
	public void rejectStartGame(RejectStartGameResponse response) {
		switch (response.getState()) {
		case playerNotEnough:
			Toolkit.getDefaultToolkit().beep();
			JOptionPane.showMessageDialog(TankController.selectModeFrame.getRoomPanel(), "游戏玩家不足，无法开始游戏", "玩家不足",
					JOptionPane.INFORMATION_MESSAGE);
			break;
		}

	}

	/**
	 * 登录
	 * 
	 * @param response
	 */
	@HandlerAnno
	public void login(LoginResponse response) {
		switch (response.getLoginState()) {
		case PasswordError:
			Toolkit.getDefaultToolkit().beep();
			JOptionPane.showMessageDialog(null, "密码错误", "密码错误", JOptionPane.ERROR_MESSAGE);
			break;
		case AccountNotExist:
			Toolkit.getDefaultToolkit().beep();
			JOptionPane.showMessageDialog(null, "账号不存在", "账号不存在", JOptionPane.ERROR_MESSAGE);
			break;
		case LoginSuccess:
			TankController.tankId = response.getId();
			TankController.password = response.getPassword();
			SelectModeFrame frame = new SelectModeFrame();
			TankController.selectModeFrame = frame;
			TankController.loginFrame.setVisible(false);
			break;
		case ReplicatedLogin:
			Toolkit.getDefaultToolkit().beep();
			JOptionPane.showMessageDialog(null, "重复登录", "重复登录", JOptionPane.ERROR_MESSAGE);
			break;
		}

	}

	/**
	 * 注册
	 * 
	 * @param response
	 */
	@HandlerAnno
	public void register(RegisterResponse response) {
		switch (response.getRegisterState()) {
		case RegisterSuccess:
			Toolkit.getDefaultToolkit().beep();
			JOptionPane.showMessageDialog(null, "注册成功", "注册成功", JOptionPane.INFORMATION_MESSAGE);
			break;
		case AccountExist:
			Toolkit.getDefaultToolkit().beep();
			JOptionPane.showMessageDialog(null, "注册失败", "注册失败，用户名重复", JOptionPane.ERROR_MESSAGE);
			break;
		}

	}

	/**
	 * 处理保存进度的响应
	 */
	@HandlerAnno
	public void handleSaveProgressResponse(SaveProgressResponse request) {
		switch (request.getState()) {
		case Success:
			Toolkit.getDefaultToolkit().beep();
			JOptionPane.showMessageDialog(TankController.selectModeFrame.getTankPanel(), "保存成功", "游戏进度保存成功",
					JOptionPane.INFORMATION_MESSAGE);
			TankController.selectModeFrame.enterModePanel(); // 保存成功，进入模式选择界面
			break;
		case Failure:
			Toolkit.getDefaultToolkit().beep();
			JOptionPane.showMessageDialog(TankController.selectModeFrame.getTankPanel(), "保存失败", "游戏进度保存失败",
					JOptionPane.ERROR_MESSAGE);
			TankController.selectModeFrame.enterModePanel(); // 保存失败，进入模式选择界面
			break;
		}
	}

	/**
	 * 处理是否有存档的响应
	 * 
	 * @param response
	 */
	@HandlerAnno
	public void handleJudgeStorageResponse(JudgeStorageResponse response) {
		int mapId = response.getMapId();
		if (response.isStorageExist()) { // 该关卡有存档
			Toolkit.getDefaultToolkit().beep();
			int res = JOptionPane.showConfirmDialog(TankController.selectModeFrame.getChapterPanel(), "该关卡有存档，是否读取存档",
					"是否读取存档", JOptionPane.YES_NO_OPTION);
			if (res == JOptionPane.YES_OPTION) { // 读取存档
				StartGameRequest request = new StartGameRequest("");
				request.setGameMode(StartGameRequest.GameMode.PVC);
				request.setTankId(TankController.tankId);
				request.setChapter(mapId);
				request.setInitMap(false);
				TankController.channel.writeAndFlush(request);
			} else if (res == JOptionPane.NO_OPTION) { // 读取原始地图
				StartGameRequest request = new StartGameRequest("");
				request.setGameMode(StartGameRequest.GameMode.PVC);
				request.setTankId(TankController.tankId);
				request.setChapter(mapId);
				request.setInitMap(true);
				TankController.channel.writeAndFlush(request);
			}
		} else { // 该关卡无存档
			Toolkit.getDefaultToolkit().beep();
			JOptionPane.showMessageDialog(TankController.selectModeFrame.getChapterPanel(), "当前关卡无存档，重新开始游戏", "无存档",
					JOptionPane.INFORMATION_MESSAGE);
			StartGameRequest request = new StartGameRequest("");
			request.setGameMode(StartGameRequest.GameMode.PVC);
			request.setTankId(TankController.tankId);
			request.setChapter(mapId);
			request.setInitMap(true);
			TankController.channel.writeAndFlush(request);
		}

	}
	
	/**
	 * 游戏胜利
	 * @param response
	 */
	@HandlerAnno
	public void handleGameSuccess(VictoryResponse response){
		Thread thread = new Thread(new GameSuccessThread(TankPanel.resource));
		thread.start();
		
	}
	
	/**
	 * 游戏失败
	 * @param response
	 */
	@HandlerAnno
	public void handleGameFailure(FailureResponse response){
		Thread thread = new Thread(new GameOverThread(TankPanel.resource));
		thread.start();
	}
	
	/**
	 * 选择关卡
	 * @param response
	 */
	@HandlerAnno
	public void handleselectMode(ChapterResponse response){
		TankController.selectModeFrame.enterSelectChapterPanel(response.getMapInfo());
	}
	
	/**
	 * 重连
	 * @param response
	 */
	@HandlerAnno
	public void handleReconnect(ReConnectResponse response){
		switch (response.getLostConnectState()) {
		case InGame: // 断线的时候，在游戏中
			if (response.isOver()) { // 游戏结束
				Toolkit.getDefaultToolkit().beep();
				JOptionPane.showMessageDialog(TankController.selectModeFrame, "游戏结束", "掉线时，你被击杀，游戏结束",
						JOptionPane.INFORMATION_MESSAGE);
				TankController.selectModeFrame.enterModePanel(); // 游戏结束，进入模式选择界面
				TankController.repaintThread.stop = true;
			} else { // 游戏未结束
				TankPanel.resource.setPlayerTanks(new ConcurrentHashMap<>(response.getPlayerTanks()));
				TankPanel.resource.setRobotTanks(new ConcurrentHashMap<>(response.getRobotTanks()));
				TankPanel.resource.getMap().setBricks(new CopyOnWriteArrayList<>(response.getBricks()));
				TankController.panel.repaint();
			}
			break;
		case NotInGame: // 断线的时候，没进入游戏
			Toolkit.getDefaultToolkit().beep();
			JOptionPane.showMessageDialog(TankController.selectModeFrame, "游戏重连成功", "游戏重连成功",
					JOptionPane.INFORMATION_MESSAGE);
			break;
		}
		
	}
}
