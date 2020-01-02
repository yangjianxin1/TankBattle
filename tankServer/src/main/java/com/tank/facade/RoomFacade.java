package com.tank.facade;

import org.springframework.stereotype.Component;

import com.tank.beanPostProcessor.HandlerAnno;
import com.tank.request.CreateRoomRequest;
import com.tank.request.EnterRoomRequest;
import com.tank.request.ExitRoomRequest;
import com.tank.service.RoomService;

import io.netty.channel.Channel;

@Component
public class RoomFacade {
	private RoomService roomService = new RoomService();

	@HandlerAnno
	public void createRoom(Channel channel, CreateRoomRequest request) { // 创建房间的请求
		roomService.createRoom(request.getUserId(), channel);
	}

	@HandlerAnno
	public void enterRoom(Channel channel, EnterRoomRequest request) { // 进入某个房间
		roomService.enterRoom(request.getRoomId(), request.getUserId(), channel);

	}

	@HandlerAnno
	public void exitRoom(Channel channel, ExitRoomRequest request) { // 玩家退出房间
		String roomId = request.getRoomId();
		String tankId = request.getTankId();
		roomService.exitRoom(roomId, tankId, channel);
	}
}
