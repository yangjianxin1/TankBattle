package com.tank.server.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

import com.tank.beanPostProcessor.ActionDispatcher;
import com.tank.request.ChapterRequest;
import com.tank.request.CloseClientRequest;
import com.tank.request.CreateBulletRequest;
import com.tank.request.CreateRoomRequest;
import com.tank.request.EnterRoomRequest;
import com.tank.request.ExitRoomRequest;
import com.tank.request.JudgeStorageRequest;
import com.tank.request.LoginRequest;
import com.tank.request.PingRequest;
import com.tank.request.ReConnectRequest;
import com.tank.request.RegisterRequest;
import com.tank.request.SavePogressRequest;
import com.tank.request.StartGameRequest;
import com.tank.request.TankMoveRequest;
import com.tank.response.JudgeStorageResponse;
import com.tank.response.LoginResponse;
import com.tank.response.PongResponse;
import com.tank.response.RegisterResponse;
import com.tank.service.BulletService;
import com.tank.service.GameService;
import com.tank.service.LoginService;
import com.tank.service.MapService;
import com.tank.service.RoomService;
import com.tank.service.TankService;

import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.handler.timeout.ReadTimeoutException;

public class TankServerHandler extends ChannelHandlerAdapter{
	private ActionDispatcher dispatcher=(ActionDispatcher) TankServer.applicationContext.getBean("actionDispatcher");
	private static Logger log = LoggerFactory.getLogger(TankServerHandler.class);

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		log.debug("RECEIVE MSG->"+msg);
		if(msg instanceof PingRequest){	//如果收到的是心跳消息，不处理
			
		}else{	//处理非心跳消息
			dispatcher.doHandle(ctx.channel(), msg);
		}
		
	}

	@Override
	public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
		ctx.flush();
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		if (cause instanceof ReadTimeoutException) {
			System.out.println("read time out");
		}
		cause.printStackTrace();
	}

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		super.channelActive(ctx);
		System.out.println("连接成功");
	}

	@Override
	public void channelInactive(ChannelHandlerContext ctx) throws Exception {
		super.channelInactive(ctx);
		System.out.println("channel inactive");
	}
	
	@Override
	public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
		super.userEventTriggered(ctx, evt);
		if (evt instanceof IdleStateEvent) {
			IdleStateEvent e = (IdleStateEvent) evt;
			if (e.state() == IdleState.READER_IDLE) {	//服务端链路读空闲，此时清除连接信息
				log.debug("链路读空闲");
				System.out.println("服务端读空闲");
				String name=TankServer.CHANNEL_NAME.get(ctx.channel());
				if(name!=null){//如果用户已登录
					String roomId=TankServer.NAME_ROOMID.get(name);
					TankServer.isLogin.remove(name);	//将登录状态设为未登录
					TankServer.CHANNEL_NAME.remove(ctx.channel());	//移除该channel
					if(roomId==null){	//如果未创建房间
						TankServer.LOSTCONNECT_STATE.put(name, false);
					}else if(TankServer.isStart.get(roomId)!=null){	//已创建房间,并且游戏开始
						TankServer.LOSTCONNECT_STATE.put(name, true);
					}
				}
				ctx.close();
			}
		}
		
	}
}
