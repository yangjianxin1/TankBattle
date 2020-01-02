package com.tank.server.handler;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.stereotype.Component;

import com.tank.entity.GameResource;
import com.tank.thread.MonitorRobotThread;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.timeout.IdleStateHandler;

public class TankServer {
	public final static ConcurrentHashMap<String, Boolean> isStart=new ConcurrentHashMap<>();	//某个房间是否已经开始游戏，key：roomId,value:是否开始的标识
	public final static ConcurrentHashMap<String, ConcurrentHashMap<String, Channel>> ROOM_CHANNELGROUP = new ConcurrentHashMap<>(); // 房间号与房间的channel集合的映射
	public final static ConcurrentHashMap<String, GameResource>  RESOURCE=new ConcurrentHashMap<>();	//每个房间对应的资源
	public final static ConcurrentHashMap<String, MonitorRobotThread> MonitorRobotThreads=new ConcurrentHashMap<>();	//对应房间的寻路线程
	public final static ConcurrentHashMap<String,Boolean> isLogin=new ConcurrentHashMap<String, Boolean>();	//用于判断用户是否已经登录的标识
	public final static ConcurrentHashMap<Channel,String> CHANNEL_NAME=new ConcurrentHashMap<>();	//channel和name的map
	public final static ConcurrentHashMap<String, String> NAME_ROOMID=new ConcurrentHashMap<>();	//玩家所在的房间
	public final static ConcurrentHashMap<String, Boolean> LOSTCONNECT_STATE=new ConcurrentHashMap<>();	//玩家掉线的时候的状态，是在游戏中，还是在游戏外。true:游戏中，false：未进入游戏
	public static ApplicationContext applicationContext=new ClassPathXmlApplicationContext("classpath:application-context.xml");
	private static Logger log = LoggerFactory.getLogger(TankServer.class);
	
	public static void main(String[] args) throws Exception {
		new TankServer().bind(8888);
	}

	public void bind(int port) throws Exception {
		log.info("服务端开始绑定端口："+port);
		EventLoopGroup bossGroup = new NioEventLoopGroup();
		EventLoopGroup workerGroup = new NioEventLoopGroup();
		try {
			ServerBootstrap b = new ServerBootstrap();
			b.group(bossGroup, workerGroup).channel(NioServerSocketChannel.class).option(ChannelOption.SO_BACKLOG, 1024)
					.childOption(ChannelOption.SO_KEEPALIVE, true).handler(new LoggingHandler(LogLevel.INFO))
					.childHandler(new ChildChannelHandler());

			ChannelFuture f = b.bind(port).sync();
			if (f.isSuccess()) {
				log.info("服务端绑定端口成功："+port);
			} else {
				log.info("服务端绑定端口是失败："+port+",失败原因："+f.cause());
			}
			f.channel().closeFuture().sync();

		} finally {
			bossGroup.shutdownGracefully();
			workerGroup.shutdownGracefully();
			log.info("服务端程序关闭");
		}
	}

	private class ChildChannelHandler extends ChannelInitializer<SocketChannel> {
		@Override
		protected void initChannel(SocketChannel ch) throws Exception {
			ch.pipeline().addLast(new IdleStateHandler(10, 0, 0,TimeUnit.SECONDS));
			ch.pipeline().addLast(MarshallingCodeCFactory.buildMarshallingDecoder());
			ch.pipeline().addLast(MarshallingCodeCFactory.buildMarshallingEncoder());
			ch.pipeline().addLast(new TankServerHandler());
		}
	}
}
