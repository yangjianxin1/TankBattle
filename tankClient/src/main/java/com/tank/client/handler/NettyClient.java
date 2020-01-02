package com.tank.client.handler;

import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.tank.request.ReConnectRequest;
import com.tank.view.LoginFrame;
import com.tank.view.controller.TankController;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.timeout.IdleStateHandler;

public class NettyClient {

	private static Bootstrap bootstrap;
	private final static String host = "localhost";
	private final static int port = 8888;
	public static ApplicationContext applicationContext=new ClassPathXmlApplicationContext("classpath:application-context.xml");
	private static Logger log = LoggerFactory.getLogger(NettyClient.class);
	private ChannelFutureListener listener=new ChannelFutureListener() {
		
		@Override
		public void operationComplete(ChannelFuture future) throws Exception {
			if (future.isSuccess()) {
				log.info("客户端连接成功");
				// 保存与服务端通信的channel
				TankController.channel = future.channel();
				TankController.loginFrame =new LoginFrame();
			} else {
				log.info("客户端连接失败");
			}
		}
	};

	public void connect() throws Exception {
		log.info("开始请求连接服务端");
		EventLoopGroup group = new NioEventLoopGroup();
		ChannelFuture f = null;
		try {
			bootstrap = new Bootstrap();
			bootstrap.group(group).channel(NioSocketChannel.class).option(ChannelOption.TCP_NODELAY, true)
					.option(ChannelOption.SO_KEEPALIVE, true).handler(new ChildChannelHandler());
			f = bootstrap.connect(host, port).sync();
			f.addListener(listener);
			f.channel().closeFuture().sync();
			f.removeListener(listener);
		} finally {
			//group.shutdownGracefully();
			//Thread.sleep(10000);
			//reConnect();
			//System.out.println("finally");
			//System.out.println(TankController.channel.isActive());
		}

		// group.shutdownGracefully();
	}

	public void reConnect() throws Exception {
		log.info("客户端开始重连");
		ChannelFuture f = null;
		try {
			f = bootstrap.connect(host, port).sync();
			f.addListener(new ChannelFutureListener() {

				@Override
				public void operationComplete(ChannelFuture f) throws Exception {
					if (f.isSuccess()) {
						// timer.cancel();
						System.out.println("重连成功");
						TankController.channel = f.channel();
						ReConnectRequest request=new ReConnectRequest();
						request.setName(TankController.tankId);
						request.setPassword(TankController.password);
						f.channel().writeAndFlush(request);
					} else {
						System.out.println("重连失败");
					}
				}
			});
			f.channel().closeFuture().sync();
		} finally {
			//Thread.sleep(10000);
			//connect();
		}

		// group.shutdownGracefully();
	}

	private class ChildChannelHandler extends ChannelInitializer<SocketChannel> {
		@Override
		protected void initChannel(SocketChannel ch) throws Exception {
			ch.pipeline().addLast(new IdleStateHandler(0, 5, 0,TimeUnit.SECONDS));
			ch.pipeline().addLast(MarshallingCodeCFactory.buildMarshallingDecoder());
			ch.pipeline().addLast(MarshallingCodeCFactory.buildMarshallingEncoder());
			// ch.pipeline().addLast(new ReadTimeoutHandler(5));
			ch.pipeline().addLast(new NettyClientHandler(NettyClient.this));
			// System.out.println(ch.id());

		}
	}

}