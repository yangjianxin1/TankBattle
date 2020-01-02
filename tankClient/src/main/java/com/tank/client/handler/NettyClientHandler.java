package com.tank.client.handler;

import java.awt.Toolkit;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.swing.JOptionPane;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tank.beanPostProcessor.ActionDispatcher;
import com.tank.request.PingRequest;
import com.tank.response.BombResponse;
import com.tank.response.BulletLocationResponse;
import com.tank.response.ChapterResponse;
import com.tank.response.CreateRoomResponse;
import com.tank.response.DestroyBrickResponse;
import com.tank.response.DestroyTankResponse;
import com.tank.response.FailureResponse;
import com.tank.response.InitialMapResponse;
import com.tank.response.JudgeStorageResponse;
import com.tank.response.LoginResponse;
import com.tank.response.PongResponse;
import com.tank.response.ReConnectResponse;
import com.tank.response.RegisterResponse;
import com.tank.response.RejectStartGameResponse;
import com.tank.response.RoomResponse;
import com.tank.response.SaveProgressResponse;
import com.tank.response.TankLocationResponse;
import com.tank.response.VictoryResponse;
import com.tank.service.BombService;
import com.tank.service.BulletService;
import com.tank.service.GameService;
import com.tank.service.MapService;
import com.tank.service.RoomService;
import com.tank.service.TankService;
import com.tank.view.TankPanel;
import com.tank.view.controller.TankController;
import com.tank.view.thread.GameOverThread;
import com.tank.view.thread.GameSuccessThread;

import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;

public class NettyClientHandler extends ChannelHandlerAdapter {

	private NettyClient client;
	private int heartbeatCount = 0;
	// 关闭链路后发起重连的间隔
	private final int INTERVAL = 5;
	// 心跳计数器能达到的最大值
	private final int MAX_HEARTBEAT_COUNT = 5;
	private ActionDispatcher dispatcher = (ActionDispatcher) NettyClient.applicationContext.getBean("actionDispatcher");
	private static Logger log = LoggerFactory.getLogger(NettyClientHandler.class);
	private TankService tankService = new TankService();
	private RoomService roomService = new RoomService();
	private GameService gameService = new GameService();
	private BulletService bulletService = new BulletService();
	private BombService BombService = new BombService();
	private MapService mapService = new MapService();

	public NettyClientHandler(NettyClient client) {
		this.client = client;
	}

	@Override
	public void channelActive(final ChannelHandlerContext ctx) {
		// new Thread(){
		// @Override
		// public void run() {
		// try {
		// Thread.sleep(10000);
		// ctx.close();
		// } catch (InterruptedException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
		// }
		// }.start();
	}

	@Override
	public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
		super.userEventTriggered(ctx, evt);
		if (evt instanceof IdleStateEvent) {
			IdleStateEvent e = (IdleStateEvent) evt;
			if (e.state() == IdleState.WRITER_IDLE) { // 链路写空闲
				log.debug("链路写空闲");
				System.out.println("客户端链路写空闲");
				ctx.writeAndFlush(new PingRequest());
			}
		}
	}

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		if (msg instanceof PongResponse) {

		} else {
			dispatcher.doHandle(msg);
		}

//		if (msg instanceof InitialMapResponse) {
//			InitialMapResponse response = (InitialMapResponse) msg;
//			mapService.initMap(response);
//		} else if (msg instanceof TankLocationResponse) {
//			TankLocationResponse response = (TankLocationResponse) msg;
//			tankService.refreshTankLocation(response);
//		} else if (msg instanceof DestroyBrickResponse) {
//			DestroyBrickResponse response = (DestroyBrickResponse) msg;
//			mapService.removeBrick(response);
//		} else if (msg instanceof DestroyTankResponse) {
//			DestroyTankResponse response = (DestroyTankResponse) msg;
//			tankService.destroyTank(response);
//		} else if (msg instanceof PongResponse) {
//
//		} else if (msg instanceof RejectStartGameResponse) {
//			RejectStartGameResponse response = (RejectStartGameResponse) msg;
//			gameService.rejectStartGame(response);
//		} else if (msg instanceof LoginResponse) { // 登陆响应
//			LoginResponse response = (LoginResponse) msg;
//			gameService.login(response);
//		} else if (msg instanceof RegisterResponse) {
//			RegisterResponse response = (RegisterResponse) msg;
//			gameService.register(response);
//		} else if (msg instanceof RoomResponse) {
//			RoomResponse response = (RoomResponse) msg;
//			roomService.handleEnterRoomResponse(response);
//		} else if (msg instanceof CreateRoomResponse) {
//			CreateRoomResponse response = (CreateRoomResponse) msg;
//			roomService.setRoomId(response);
//		} else if (msg instanceof VictoryResponse) {
//			Thread thread = new Thread(new GameSuccessThread(TankPanel.resource));
//			thread.start();
//		} else if (msg instanceof FailureResponse) { // 游戏失败
//			Thread thread = new Thread(new GameOverThread(TankPanel.resource));
//			thread.start();
//		} else if (msg instanceof BulletLocationResponse) {
//			BulletLocationResponse response = (BulletLocationResponse) msg;
//			bulletService.refreshBulletLocation(response);
//		} else if (msg instanceof BombResponse) {
//			BombResponse response = (BombResponse) msg;
//			BombService.createBomb(response);
//		} else if (msg instanceof ChapterResponse) {
//			ChapterResponse response = (ChapterResponse) msg;
//			TankController.selectModeFrame.enterSelectChapterPanel(response.getMapInfo());
//		} else if (msg instanceof SaveProgressResponse) { // 收到保存进度的响应
//			SaveProgressResponse request = (SaveProgressResponse) msg;
//			gameService.handleSaveProgressResponse(request);
//		} else if (msg instanceof JudgeStorageResponse) {
//			JudgeStorageResponse response = (JudgeStorageResponse) msg;
//			gameService.handleJudgeStorageResponse(response);
//		} else if (msg instanceof ReConnectResponse) {
//			ReConnectResponse response = (ReConnectResponse) msg;
//			switch (response.getLostConnectState()) {
//			case InGame: // 断线的时候，在游戏中
//				if (response.isOver()) { // 游戏结束
//					Toolkit.getDefaultToolkit().beep();
//					JOptionPane.showMessageDialog(TankController.selectModeFrame, "游戏结束", "掉线时，你被击杀，游戏结束",
//							JOptionPane.INFORMATION_MESSAGE);
//					TankController.selectModeFrame.enterModePanel(); // 游戏结束，进入模式选择界面
//					TankController.repaintThread.stop = true;
//				} else { // 游戏未结束
//					TankPanel.resource.setPlayerTanks(new ConcurrentHashMap<>(response.getPlayerTanks()));
//					TankPanel.resource.setRobotTanks(new ConcurrentHashMap<>(response.getRobotTanks()));
//					TankPanel.resource.getMap().setBricks(new CopyOnWriteArrayList<>(response.getBricks()));
//					TankController.panel.repaint();
//				}
//				break;
//			case NotInGame: // 断线的时候，没进入游戏
//				Toolkit.getDefaultToolkit().beep();
//				JOptionPane.showMessageDialog(TankController.selectModeFrame, "游戏重连成功", "游戏重连成功",
//						JOptionPane.INFORMATION_MESSAGE);
//				break;
//			}
//		}
	}

	@Override
	public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
		ctx.flush();
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
		cause.printStackTrace();
		ctx.close();
	}

	@Override
	public void close(ChannelHandlerContext ctx, ChannelPromise promise) throws Exception {
		super.close(ctx, promise);
		System.out.println("close");
	}

	@Override
	public void channelInactive(ChannelHandlerContext ctx) throws Exception {
		super.channelInactive(ctx);
		System.out.println("客户端channelInactive");
		ctx.close();
		client.reConnect();
	}
}