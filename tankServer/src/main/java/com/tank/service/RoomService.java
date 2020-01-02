package com.tank.service;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import com.tank.request.CreateRoomRequest;
import com.tank.request.EnterRoomRequest;
import com.tank.response.CreateRoomResponse;
import com.tank.response.RoomResponse;
import com.tank.response.RoomResponse.RoomState;
import com.tank.server.handler.TankServer;

import io.netty.channel.Channel;

/**
 * 控制房间的逻辑
 * 
 * @author Administrator
 *
 */
public class RoomService {
	/**
	 * 处理退出房间的请求
	 * 
	 * @param roomId
	 * @param tankId
	 */
	public void exitRoom(String roomId, String tankId, Channel ctx) {
		TankServer.ROOM_CHANNELGROUP.get(roomId).remove(tankId);
		TankServer.NAME_ROOMID.remove(tankId);
		// 给该房间的所有其他玩家发送更新房间信息的信息
		ConcurrentHashMap<String, Channel> roomChannel = TankServer.ROOM_CHANNELGROUP.get(roomId); // 获得该房间对应的所有channel
		Enumeration<String> userIdEnum = roomChannel.keys();
		List<String> userIds = new ArrayList<>();
		while (userIdEnum.hasMoreElements()) {
			userIds.add(userIdEnum.nextElement());
		}
		RoomResponse response = new RoomResponse();
		response.setRoomState(RoomState.RoomExist);
		response.setRoomId(roomId);
		response.setPlayerIds(userIds);
		for (Channel channel : roomChannel.values()) { // 向该房间的用户，广播该房间的用户id
			channel.writeAndFlush(response);
		}

	}

	/**
	 * 进入房间
	 * 
	 * @param roomId
	 * @param tankId
	 * @param ctx
	 */
	public void enterRoom(String roomId, String tankId, Channel ctx) {
		ConcurrentHashMap<String, Channel> roomChannel = TankServer.ROOM_CHANNELGROUP.get(roomId); // 获得该房间对应的所有channel
		if (roomChannel == null) { // 如果不存在该房间号
			RoomResponse response = new RoomResponse();
			response.setRoomState(RoomState.RoomNoExist);
			ctx.writeAndFlush(response);
		} else if (TankServer.isStart.get(roomId) == null) { // 该房间存在,并且未开始游戏
			roomChannel.put(tankId, ctx); // 将该channel加入该房间
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
			for (Channel channel : roomChannel.values()) { // 向该房间的用户，广播该房间的用户id
				channel.writeAndFlush(response);
			}
			TankServer.NAME_ROOMID.put(tankId, roomId);
		} else if (TankServer.isStart.get(roomId) == true) { // 如果游戏已经开始，无法进入该房间
			RoomResponse response = new RoomResponse();
			response.setRoomState(RoomState.GameIsSatrted);
			ctx.writeAndFlush(response);
		}

	}

	/**
	 * 创建房间
	 * 
	 * @param tankId
	 * @param channel
	 */
	public void createRoom(String tankId, Channel channel) {
		// 创建全局唯一id，作为房间号
		String roomId = UUID.randomUUID().toString();
		roomId = roomId.replace("-", "");
		ConcurrentHashMap<String, Channel> map = new ConcurrentHashMap<>();
		map.put(tankId, channel);
		TankServer.ROOM_CHANNELGROUP.put(roomId, map);
		TankServer.NAME_ROOMID.put(tankId, roomId);
		CreateRoomResponse response = new CreateRoomResponse();
		response.setRoomId(roomId);
		channel.writeAndFlush(response);
	}
}
