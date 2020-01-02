package com.tank.facade;

import org.springframework.stereotype.Component;

import com.tank.beanPostProcessor.HandlerAnno;
import com.tank.request.CloseClientRequest;
import com.tank.request.JudgeStorageRequest;
import com.tank.request.LoginRequest;
import com.tank.request.ReConnectRequest;
import com.tank.request.RegisterRequest;
import com.tank.request.SavePogressRequest;
import com.tank.request.StartGameRequest;
import com.tank.response.JudgeStorageResponse;
import com.tank.response.LoginResponse;
import com.tank.response.RegisterResponse;
import com.tank.service.GameService;

import io.netty.channel.Channel;

@Component
public class GameFacade {
	private GameService gameService = new GameService();

	@HandlerAnno
	public void login(Channel channel, LoginRequest request) {// 登陆请求
		String name = request.getId();
		String password = request.getPassword();
		LoginResponse response = gameService.login(name, password, channel);
		channel.writeAndFlush(response);
	}

	@HandlerAnno
	public void startGame(Channel channel, StartGameRequest request) {	//开始游戏
		gameService.startGame(request, channel);
	}

	@HandlerAnno
	public void register(Channel channel, RegisterRequest request) {// 注册请求
		RegisterResponse response = gameService.register(request);
		channel.write(response);
	}

	@HandlerAnno
	public void closeClient(Channel channel, CloseClientRequest request) {
		// 客户端关闭窗口
		String roomId = request.getRoomId();
		String tankId = request.getTankId();
		gameService.handleCloseClient(roomId, tankId, channel);

	}

	@HandlerAnno
	public void savePogress(Channel channel, SavePogressRequest request) {
		// 保存游戏进度
		gameService.saveProgress(request, channel);

	}

	@HandlerAnno
	public void judgeStorage(Channel channel, JudgeStorageRequest request) {
		// 判断某个关卡是否有存档
		JudgeStorageResponse response = gameService.judgeStorage(request);
		channel.writeAndFlush(response);

	}

	@HandlerAnno
	public void reConnect(Channel channel, ReConnectRequest request) {
		// 断后重连请求
		String name = request.getName();
		String password = request.getPassword();
		gameService.reConnect(name, password, channel);

	}

}
