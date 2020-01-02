package com.tank.facade;

import org.springframework.stereotype.Component;

import com.tank.beanPostProcessor.HandlerAnno;
import com.tank.request.LoginRequest;
import com.tank.request.TankMoveRequest;
import com.tank.response.LoginResponse;
import com.tank.service.TankService;

import io.netty.channel.Channel;

@Component
public class TankFacade {

	private TankService tankService = new TankService();

	@HandlerAnno
	public void tankMove(Channel channel, TankMoveRequest request) {
		String roomId = request.getRoomId();
		String tankId = request.getTankId();
		int movingDirect = request.getMovingDirect();
		tankService.judgeCrash(roomId, tankId, movingDirect);
	}
}
