package com.tank.facade;

import org.springframework.stereotype.Component;

import com.tank.beanPostProcessor.HandlerAnno;
import com.tank.request.ChapterRequest;
import com.tank.service.MapService;

import io.netty.channel.Channel;

@Component
public class MapFacade {
	private MapService mapService = new MapService();

	@HandlerAnno
	public void startGame(Channel channel, ChapterRequest request) {
		channel.writeAndFlush(mapService.selectChapters());
	}
}
