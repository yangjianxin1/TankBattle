package com.tank.facade;

import org.springframework.stereotype.Component;

import com.tank.beanPostProcessor.HandlerAnno;
import com.tank.request.CreateBulletRequest;
import com.tank.service.BulletService;

import io.netty.channel.Channel;

@Component
public class BulletFacade {
	private BulletService bulletService = new BulletService();

	@HandlerAnno
	public void createBullet(Channel channel, CreateBulletRequest request) {
		String roomId = request.getRoomId();
		String tankId = request.getTankId();
		bulletService.createBullet(tankId, roomId);

	}
}
