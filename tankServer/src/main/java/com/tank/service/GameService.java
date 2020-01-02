package com.tank.service;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.tank.dao.MapDao;
import com.tank.dao.PlayerDao;
import com.tank.dao.entity.MapEntity;
import com.tank.entity.Brick;
import com.tank.entity.Element;
import com.tank.entity.GameResource;
import com.tank.entity.Iron;
import com.tank.entity.RobotTank;
import com.tank.entity.Tank;
import com.tank.entity.Water;
import com.tank.request.RegisterRequest;
import com.tank.request.SavePogressRequest;
import com.tank.request.JudgeStorageRequest;
import com.tank.request.StartGameRequest;
import com.tank.response.InitialMapResponse;
import com.tank.response.InitialMapResponse.GameMode;
import com.tank.response.JudgeStorageResponse;
import com.tank.response.LoginResponse;
import com.tank.response.LoginResponse.LoginState;
import com.tank.response.ReConnectResponse;
import com.tank.response.ReConnectResponse.LostConnectState;
import com.tank.response.RegisterResponse;
import com.tank.response.RegisterResponse.RegisterState;
import com.tank.response.RejectStartGameResponse;
import com.tank.response.RejectStartGameResponse.RejectStartGameState;
import com.tank.response.RoomResponse;
import com.tank.response.RoomResponse.RoomState;
import com.tank.response.SaveProgressResponse;
import com.tank.response.SaveProgressResponse.State;
import com.tank.server.handler.TankServer;
import com.tank.server.handler.TankServerHandler;
import com.tank.thread.MonitorRobotThread;

import io.netty.channel.Channel;

/**
 * 处理开始游戏，结束游戏等逻辑
 * 
 * @author Administrator
 *
 */
@Service
public class GameService {
	private PlayerDao playerDao = new PlayerDao();
	private MapDao mapDao = new MapDao();
	private static Logger log = LoggerFactory.getLogger(GameService.class);

	/**
	 * 处理关闭客户端窗口的请求
	 * 
	 * @param roomId
	 * @param tankId
	 * @param ctx
	 */
	public void handleCloseClient(String roomId, String tankId, Channel ctx) {
		// 客户端关闭窗口
		if (roomId == null) { // 未进入房间
			System.out.println("close client");
			ctx.close();
		} else if (TankServer.isStart.get(roomId) == null) { // 已进入房间,但未开始游戏
			// 将当前channel从该房间移除
			TankServer.ROOM_CHANNELGROUP.get(roomId).remove(tankId);
			TankServer.NAME_ROOMID.remove(tankId);
			ctx.close();
			ConcurrentHashMap<String, Channel> roomChannel = TankServer.ROOM_CHANNELGROUP.get(roomId); // 获得该房间对应的所有channel
			// 获取该房间内的所有的用户id
			Enumeration<String> userIdEnum = roomChannel.keys();
			List<String> userIds = new ArrayList<>();
			while (userIdEnum.hasMoreElements()) {
				userIds.add(userIdEnum.nextElement());
			}
			RoomResponse response = new RoomResponse();
			response.setRoomState(RoomState.RoomExist);
			response.setRoomId(roomId);
			response.setPlayerIds(userIds);
			// 给该房间的其他玩家发送"房间信息"
			for (Channel channel : roomChannel.values()) { // 向该房间的用户，广播该房间的用户id
				channel.writeAndFlush(response);
			}
		} else {// 游戏已开始
				// 将当前channel从该房间移除
			TankServer.ROOM_CHANNELGROUP.get(roomId).remove(tankId);
			if (TankServer.MonitorRobotThreads.get(roomId) != null) { // 如果是人机对战,结束寻路线程
				TankServer.MonitorRobotThreads.get(roomId).stopFlag = true;
				TankServer.MonitorRobotThreads.remove(roomId);
			}
			if (TankServer.ROOM_CHANNELGROUP.get(roomId).size() <= 0) { // 如果该房间所有玩家都退出游戏
				TankServer.RESOURCE.remove(roomId);
				TankServer.isStart.remove(roomId);
				System.out.println("移除resource");
			}
			ctx.close();
		}
		TankServer.CHANNEL_NAME.remove(tankId);
		if(tankId!=null){
			TankServer.isLogin.remove(tankId);	//清除该用户的登录状态
		}
	}

	/**
	 * 登陆
	 * 
	 * @param name
	 * @param password
	 * @return
	 */
	public LoginResponse login(String name, String password,Channel channel) {
		LoginResponse response = new LoginResponse();
		if(TankServer.isLogin.get(name)!=null){	//如果不为空，表明该用户已登录
			response.setLoginState(LoginState.ReplicatedLogin);
		}else if (playerDao.playerExist(name) == false) { // 账号不存在
			response.setLoginState(LoginState.AccountNotExist);
		} else if (playerDao.validatePassword(name, password) == true) { // 密码正确，登陆成功
			response.setLoginState(LoginState.LoginSuccess);
			TankServer.isLogin.put(name, true);	//设置该用户为已登录
			TankServer.CHANNEL_NAME.put(channel, name);
		} else { // 密码错误登陆失败
			response.setLoginState(LoginState.PasswordError);
		}
		response.setId(name);
		response.setPassword(password);
		return response;
	}
	
	public void reConnect(String name,String password,Channel channel){
		if(TankServer.isLogin.get(name)!=null){	//如果不为空，表明该用户已登录,此时为重复登录
			LoginResponse response = new LoginResponse();
			response.setLoginState(LoginState.ReplicatedLogin);
			response.setId(name);
			channel.writeAndFlush(response);
			return;
		}else if (playerDao.playerExist(name) == false) { // 账号不存在
			LoginResponse response = new LoginResponse();
			response.setLoginState(LoginState.AccountNotExist);
			response.setId(name);
			channel.writeAndFlush(response);
			return;
		}else if (!playerDao.validatePassword(name, password)){ // 密码错误登陆失败
			LoginResponse response = new LoginResponse();
			response.setLoginState(LoginState.PasswordError);
			response.setId(name);
			channel.writeAndFlush(response);
			return;
		} else if (playerDao.validatePassword(name, password)) { // 密码正确，用户验证成功
			ReConnectResponse response=new ReConnectResponse();
			TankServer.isLogin.put(name, true);	//设置该用户为已登录
			TankServer.CHANNEL_NAME.put(channel, name);
			if(TankServer.LOSTCONNECT_STATE.get(name)){	//离线时，玩家在游戏中
				log.debug("离线时，玩家在游戏中");
				System.out.println("离线时，玩家在游戏中");
				String roomId=TankServer.NAME_ROOMID.get(name);	//获取
				log.debug("roomId:"+roomId);
				System.out.println("roomId:"+roomId);
				if(roomId==null){	//重连成功后，游戏已结束
					System.out.println("重连成功后，游戏已结束");
					log.debug("重连成功后，游戏已结束");
					response.setLostConnectState(LostConnectState.InGame);
					response.setOver(true);
				}else{	//重连成功后，游戏未结束
					System.out.println("重连成功后，游戏未结束");
					log.debug("重连成功后，游戏未结束");
					TankServer.ROOM_CHANNELGROUP.get(roomId).put(name, channel);
					GameResource resource=TankServer.RESOURCE.get(roomId);
					response.setOver(false);
					response.setBricks(resource.getBricks());
					response.setPlayerTanks(resource.getPlayerTanks());
					resource.setRobotTanks(resource.getRobotTanks());
					response.setLostConnectState(LostConnectState.InGame);
				}
			}else{	//离线时，玩家在游戏外
				response.setLostConnectState(LostConnectState.NotInGame);
			}
			channel.writeAndFlush(response);
		} 
	}
	

	public RegisterResponse register(RegisterRequest request) {
		String id = request.getId();
		String password = request.getPassword();
		RegisterResponse response = new RegisterResponse();
		if (playerDao.playerExist(id)) { // 用户id已存在
			response.setRegisterState(RegisterState.AccountExist);
		} else if (playerDao.insertPlayer(id, password)) {
			response.setRegisterState(RegisterState.RegisterSuccess);
		}
		return response;
	}

	public void startGame(StartGameRequest request, Channel channel) {
		String tankId = request.getTankId();
		String roomId = request.getRoomId();
		int mapId = request.getChapter();
		switch (request.getGameMode()) {
		case PVC:
			pvcStartGame(roomId, tankId, channel, mapId,request.isInitMap());
			break;
		case PVP:
			pvpStartGame(roomId, channel);
			break;
		}
	}

	/**
	 * 保存当前游戏进度
	 * 
	 * @param request
	 */
	public void saveProgress(SavePogressRequest request, Channel channel) {
		String roomId = request.getRoomId();
		String tankId = request.getTankId();
		TankServer.isStart.remove(roomId);
		TankServer.MonitorRobotThreads.get(roomId).stopFlag = true; // 结束重画线程
		int playerId = mapDao.selectPlayerId(tankId);
		mapDao.deleteProgress(TankServer.RESOURCE.get(roomId).getMapId(), playerId); // 删除之前的存档记录
		boolean flag = mapDao.saveProgress(TankServer.RESOURCE.get(roomId), playerId); // 保存新的存档记录
		SaveProgressResponse response = new SaveProgressResponse();
		if (flag) {
			response.setState(State.Success);
		} else {
			response.setState(State.Failure);
		}
		channel.writeAndFlush(response);
		TankServer.ROOM_CHANNELGROUP.remove(roomId);
		TankServer.RESOURCE.remove(roomId);
	}

	/**
	 * 判断当前关卡是否有存档
	 * 
	 * @param request
	 */
	public JudgeStorageResponse judgeStorage(JudgeStorageRequest request) {
		String tankId = request.getTankId();
		int mapId = request.getMapId();
		int playerId = mapDao.selectPlayerId(tankId);
		JudgeStorageResponse response = new JudgeStorageResponse();
		response.setMapId(mapId);
		response.setPlayerId(playerId);
		if (mapDao.isStore(mapId, playerId)) { // 是否有存档
			response.setStorageExist(true);
		} else {
			response.setStorageExist(false);
		}
		return response;
	}

	
	private void pvpStartGame(String roomId, Channel ctx) {
		if (TankServer.ROOM_CHANNELGROUP.get(roomId).size() < 2) { // 玩家不足，无法开始游戏
			RejectStartGameResponse response = new RejectStartGameResponse();
			response.setState(RejectStartGameState.playerNotEnough);
			ctx.writeAndFlush(response);
		} else if (TankServer.isStart.get(roomId) == null) { // 游戏未开始，初始化地图，并发送地图给该房间内的所有玩家
			TankServer.isStart.put(roomId, true);
			Enumeration<String> names = TankServer.ROOM_CHANNELGROUP.get(roomId).keys();
			InitialMapResponse response = CreateMap(1, names);
			GameResource resource = createGameResource(response);
			resource.setMapId(1);
			resource.setType(true);
			TankServer.RESOURCE.put(roomId, resource); // 设置对应房间的资源
			response.setGameMode(GameMode.PVP);
			// 向该房间内的玩家，广播地图信息
			for (Channel channel : TankServer.ROOM_CHANNELGROUP.get(roomId).values()) {
				channel.writeAndFlush(response);
			}
		}
	}

	private void pvcStartGame(String roomId, String tankId, Channel ctx, int mapId,boolean initMap) {
		int playerId = mapDao.selectPlayerId(tankId);
		InitialMapResponse response;
		if (initMap) {// 加载原始地图 
			response = CreateRobotMap(mapId, tankId);
		} else { // 加载存档的存档
			response = loadStorageMap(mapId, tankId);
		}
		response.setGameMode(GameMode.PVC);
		// 创建全局唯一id，作为房间号
		String roomId_ = UUID.randomUUID().toString();
		roomId_ = roomId_.replace("-", "");
		GameResource resource = createGameResource(response);
		resource.setMapId(mapId);
		resource.setType(false);
		TankServer.RESOURCE.put(roomId_, resource); // 设置对应房间的资源
		TankServer.NAME_ROOMID.put(tankId, roomId_);
		ConcurrentHashMap<String, Channel> map = new ConcurrentHashMap<>();
		map.put(tankId, ctx);
		TankServer.ROOM_CHANNELGROUP.put(roomId_, map);
		TankServer.isStart.put(roomId_, true);
		response.setRoomId(roomId_);
		MonitorRobotThread monitorRobotThread = new MonitorRobotThread(roomId_, tankId);
		monitorRobotThread.start();
		TankServer.MonitorRobotThreads.put(roomId_, monitorRobotThread);
		ctx.writeAndFlush(response);
	}

	/**
	 * 加载存储的地图
	 * 
	 * @param mapId
	 * @param name
	 * @return
	 */
	private InitialMapResponse loadStorageMap(int mapId, String name) {
		InitialMapResponse response = new InitialMapResponse();
		List<MapEntity> list = new ArrayList<>();
		List<Brick> bricks = new ArrayList<>();
		List<Water> waters = new ArrayList<>();
		List<Iron> irons = new ArrayList<>();
		Map<String, RobotTank> robots = new HashMap<>();
		Map<String, Tank> playerTanks = new HashMap<>();
		int playerId = mapDao.selectPlayerId(name);
		list.addAll(mapDao.selectIronAndWater(mapId));
		list.addAll(mapDao.selectStorage(mapId, playerId));
		int robotID = 1;
		for (MapEntity entity : list) {
			switch (entity.getType()) {
			case MapEntity.BRICK:
				Brick brick = new Brick(entity.getX(), entity.getY());
				bricks.add(brick);
				break;
			case MapEntity.WATER:
				Water water = new Water(entity.getX(), entity.getY());
				waters.add(water);
				break;
			case MapEntity.IRON:
				Iron iron = new Iron(entity.getX(), entity.getY());
				irons.add(iron);
				break;
			case MapEntity.ROBOT:
				RobotTank robot = new RobotTank(entity.getX(), entity.getY(), Tank.NORTH, "robot" + robotID);
				robots.put("robot" + robotID, robot);
				robotID++;
				break;
			case MapEntity.PLAYER_TANK:
				Tank tank = new Tank(entity.getX(), entity.getY(), Tank.NORTH, name);
				playerTanks.put(name, tank);
				break;
			}
		}
		response.setBricks(bricks);
		response.setIrons(irons);
		response.setWaters(waters);
		response.setPlayerTanks(playerTanks);
		response.setRobots(robots);
		return response;
	}

	/**
	 * 创建pvc的地图
	 * 
	 * @param mapId
	 * @param name
	 * @return
	 */
	private InitialMapResponse CreateRobotMap(int mapId, String name) {

		List<MapEntity> list = mapDao.selectMap(mapId);
		List<Brick> bricks = new ArrayList<>();
		List<Water> waters = new ArrayList<>();
		List<Iron> irons = new ArrayList<>();
		Map<String, RobotTank> robots = new HashMap<>();
		Map<String, Tank> playerTanks = new HashMap<>();
		int robotID = 1;
		for (MapEntity entity : list) {
			switch (entity.getType()) {
			case MapEntity.BRICK:
				Brick brick = new Brick(entity.getX(), entity.getY());
				bricks.add(brick);
				break;
			case MapEntity.WATER:
				Water water = new Water(entity.getX(), entity.getY());
				waters.add(water);
				break;
			case MapEntity.IRON:
				Iron iron = new Iron(entity.getX(), entity.getY());
				irons.add(iron);
				break;
			case MapEntity.ROBOT:
				RobotTank robot = new RobotTank(entity.getX(), entity.getY(), Tank.NORTH, "robot" + robotID);
				robots.put("robot" + robotID, robot);
				robotID++;
				break;
			case MapEntity.PLAYER_TANK:
				Tank tank = new Tank(entity.getX(), entity.getY(), Tank.NORTH, name);
				playerTanks.put(name, tank);
			}
		}

		InitialMapResponse response = new InitialMapResponse();
		response.setBricks(bricks);
		response.setIrons(irons);
		response.setWaters(waters);
		response.setPlayerTanks(playerTanks);
		response.setRobots(robots);
		return response;

	}

	/**
	 * 返回地图消息
	 * 
	 * @param mapId
	 * @return
	 */
	private InitialMapResponse CreateMap(int mapId, Enumeration<String> names) {
		List<MapEntity> list = mapDao.selectMap(mapId);
		List<Brick> bricks = new ArrayList<>();
		List<Water> waters = new ArrayList<>();
		List<Iron> irons = new ArrayList<>();
		Map<String, RobotTank> robots = new HashMap<>();
		Map<String, Tank> playerTanks = new HashMap<>();
		int robotID = 1;
		for (MapEntity entity : list) {
			switch (entity.getType()) {
			case MapEntity.BRICK:
				Brick brick = new Brick(entity.getX(), entity.getY());
				bricks.add(brick);
				break;
			case MapEntity.WATER:
				Water water = new Water(entity.getX(), entity.getY());
				waters.add(water);
				break;
			case MapEntity.IRON:
				Iron iron = new Iron(entity.getX(), entity.getY());
				irons.add(iron);
				break;
			}
		}
		while (names.hasMoreElements()) {
			String name = names.nextElement();
			boolean isCrash;
			Tank tank;
			do {
				int x = (int) (Math.random() * 500) + 20;
				int y = (int) (Math.random() * 500) + 20;
				tank = new Tank(x, y, Element.NORTH, name);
				isCrash = isCrash(tank, playerTanks, bricks, waters, irons, robots);
			} while (isCrash == true);
			playerTanks.put(name, tank);

		}

		InitialMapResponse response = new InitialMapResponse();
		response.setBricks(bricks);
		response.setIrons(irons);
		response.setWaters(waters);
		response.setPlayerTanks(playerTanks);
		response.setRobots(robots);
		return response;
	}

	/**
	 * 根据InitialMapResponse构造GameResource
	 * 
	 * @param response
	 * @return
	 */
	private GameResource createGameResource(InitialMapResponse response) {
		GameResource gameResource = new GameResource();
		CopyOnWriteArrayList<Brick> bricks = new CopyOnWriteArrayList<>(response.getBricks());
		CopyOnWriteArrayList<Water> waters = new CopyOnWriteArrayList<>(response.getWaters());
		CopyOnWriteArrayList<Iron> irons = new CopyOnWriteArrayList<>(response.getIrons());
		ConcurrentHashMap<String, Tank> playerTanks = new ConcurrentHashMap<>(response.getPlayerTanks());
		ConcurrentHashMap<String, RobotTank> robotTanks = new ConcurrentHashMap<>(response.getRobots());
		gameResource.setBricks(bricks);
		gameResource.setWaters(waters);
		gameResource.setIrons(irons);
		gameResource.setPlayerTanks(playerTanks);
		gameResource.setRobotTanks(robotTanks);
		return gameResource;
	}

	/**
	 * 产生的坦克是否与地图上的其他物体重叠
	 * 
	 * @param bricks
	 * @param waters
	 * @param irons
	 * @return
	 */
	private boolean isCrash(Tank tank, Map<String, Tank> playerTanks, List<Brick> bricks, List<Water> waters,
			List<Iron> irons, Map<String, RobotTank> robots) {
		for (Tank otherTank : playerTanks.values()) { // 判断坦克是否与其他坦克重叠
			if (!tank.getTankId().equals(otherTank.getTankId())) {
				if (tank.Overlap(otherTank) == true) {
					return true;
				}
			}
		}
		for (RobotTank robot : robots.values()) { // 判断坦克是否与机器坦克重叠
			if (tank.Overlap(robot) == true) {
				return true;
			}
		}
		for (int j = 0; j < bricks.size(); j++) { // 判断我的坦克是否与砖块重叠
			if (tank.Overlap(bricks.get(j)) == true) {
				return true;
			}
		}
		for (int j = 0; j < irons.size(); j++) { // 判断我的坦克是否与铁块重叠
			if (tank.Overlap(irons.get(j)) == true) {
				return true;
			}
		}
		for (int j = 0; j < waters.size(); j++) { // 判断我的坦克是否与河流重叠
			if (tank.Overlap(waters.get(j)) == true) {
				return true;
			}
		}
		return false;
	}
}
