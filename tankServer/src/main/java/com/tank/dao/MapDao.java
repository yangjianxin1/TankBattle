package com.tank.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tank.dao.entity.MapEntity;
import com.tank.entity.Brick;
import com.tank.entity.Chapter;
import com.tank.entity.GameResource;
import com.tank.entity.RobotTank;
import com.tank.entity.Tank;

public class MapDao {
	
	private static Logger log = LoggerFactory.getLogger(MapDao.class);
	
	public static void main(String[] args) {
		new MapDao().createMap3();
		new MapDao().createMap4();
	}

	/**
	 * 获得原始地图信息
	 * 
	 * @param mapId
	 * @return
	 */
	public List<MapEntity> selectMap(int mapId) {
		log.info("SELECT MAP->mapId："+mapId);
		List<MapEntity> list = new ArrayList<>();
		String sql = "select * from map_element where map_id=?;";
		Connection conn = DBHelper.getConnection();
		PreparedStatement state;
		try {
			state = conn.prepareStatement(sql);
			state.setInt(1, mapId);
			ResultSet rs = state.executeQuery();
			while (rs.next()) {
				MapEntity entity = new MapEntity(rs.getInt("x"), rs.getInt("y"), rs.getInt("type"),
						rs.getInt("map_id"));
				list.add(entity);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			log.error(e.toString());
		}
		return list;
	}

	/**
	 * 添加一个障碍物
	 * 
	 * @param x
	 * @param y
	 * @param type
	 * @param mapId
	 */
	public void insertElement(int x, int y, int type, int mapId) {
		String sql = "insert into map_element(x,y,type,map_id) values(?,?,?,?);";
		Connection conn = DBHelper.getConnection();
		PreparedStatement state;
		try {
			state = conn.prepareStatement(sql);
			state.setInt(1, x);
			state.setInt(2, y);
			state.setInt(3, type);
			state.setInt(4, mapId);
			int count = state.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 创建地图1
	 */
	public void createMap1() {

		MapDao dao = new MapDao();
		// 画出砖块
		for (int i = 0; i < 30; i++) {
			if (i % 2 == 0)
				continue;
			dao.insertElement(20 * i + 10, 470, MapEntity.BRICK, 1);
		}
		// 画出河流
		for (int i = 0; i < 25; i++) {
			dao.insertElement(20 * i + 60, 130, MapEntity.WATER, 1);
		}
		for (int i = 8; i < 15; i++) {
			dao.insertElement(100, 20 * i + 120, MapEntity.WATER, 1);
		}
		for (int i = 0; i < 25; i++) {
			if (i == 11 || i == 12 || i == 13)
				continue;
			dao.insertElement(20 * i + 60, 60, MapEntity.WATER, 1);
		}
		// 画出铁块
		for (int i = 0; i < 29; i++) {
			if (i % 2 == 0 || i % 3 == 0)
				continue;
			dao.insertElement(20 * i + 10, 540, MapEntity.IRON, 1);
		}
		dao.insertElement(10, 540, MapEntity.IRON, 1);
		for (int i = 0; i < 27; i++) {
			if (i % 3 == 0)
				continue;
			if (i >= 10 && i <= 15)
				continue;
			dao.insertElement(20 * i - 10, 200, MapEntity.IRON, 1);
		}

		dao.insertElement(230, 150, MapEntity.IRON, 1);
		dao.insertElement(230, 170, MapEntity.IRON, 1);
		dao.insertElement(290, 200, MapEntity.IRON, 1);
		dao.insertElement(290, 290, MapEntity.IRON, 1);
		dao.insertElement(310, 290, MapEntity.IRON, 1);
		dao.insertElement(290, 310, MapEntity.IRON, 1);
		dao.insertElement(310, 310, MapEntity.IRON, 1);
		dao.insertElement(590, 400, MapEntity.IRON, 1);
		dao.insertElement(570, 400, MapEntity.IRON, 1);

		dao.insertElement(200, 290, MapEntity.BRICK, 1);
		dao.insertElement(220, 290, MapEntity.BRICK, 1);
		dao.insertElement(200, 310, MapEntity.BRICK, 1);
		dao.insertElement(220, 310, MapEntity.BRICK, 1);

		dao.insertElement(380, 290, MapEntity.WATER, 1);
		dao.insertElement(400, 290, MapEntity.WATER, 1);
		dao.insertElement(380, 310, MapEntity.WATER, 1);
		dao.insertElement(400, 310, MapEntity.WATER, 1);

	}

	public void createMap2() {
		MapDao dao = new MapDao();
		for (int i = 0; i < 25; i++) {
			if (i % 2 == 0)
				continue;
			if (i == 11 || i == 12 || i == 13)
				continue;
			dao.insertElement(20 * i + 60, 60, MapEntity.IRON, 2);
		}
		for (int i = 0; i < 25; i++) {
			if (i == 11 || i == 12 || i == 13)
				continue;
			dao.insertElement(20 * i + 60, 540, MapEntity.IRON, 2);
		}
		for (int i = 0; i < 25; i++) {
			if (i % 2 == 0)
				continue;
			if (i == 11 || i == 12 || i == 13)
				continue;
			dao.insertElement(60, 20 * i + 60, MapEntity.WATER, 2);
		}
		for (int i = 0; i < 25; i++) {
			if (i % 2 == 0)
				continue;
			if (i == 11 || i == 12 || i == 13)
				continue;
			dao.insertElement(540, 20 * i + 60, MapEntity.WATER, 2);
		}
		dao.insertElement(290, 290, MapEntity.WATER, 2);
		dao.insertElement(310, 290, MapEntity.WATER, 2);
		dao.insertElement(290, 310, MapEntity.WATER, 2);
		dao.insertElement(310, 310, MapEntity.WATER, 2);
	}

	public void createMap3() {
		MapDao dao = new MapDao();
		for (int i = 0; i < 25; i++) {
			dao.insertElement(20 * i + 60, 60, MapEntity.BRICK, 3);
		}
		for (int i = 0; i < 25; i++) {
			dao.insertElement(20 * i + 60, 140, MapEntity.BRICK, 3);
		}
		for (int i = 0; i < 25; i++) {
			dao.insertElement(20 * i + 60, 220, MapEntity.BRICK, 3);
		}
		for (int i = 0; i < 25; i++) {
			if (i == 11 || i == 12 || i == 13)
				continue;
			dao.insertElement(20 * i + 60, 460, MapEntity.BRICK, 3);
		}
		for (int i = 0; i < 25; i++) {
			if (i == 11 || i == 12 || i == 13)
				continue;
			dao.insertElement(20 * i + 60, 540, MapEntity.BRICK, 3);
		}
		for (int i = 0; i < 25; i++) {
			dao.insertElement(60, 20 * i + 60, MapEntity.BRICK, 3);
		}
		for (int i = 0; i < 25; i++) {
			Brick Brick = new Brick(540, 20 * i + 60);
			dao.insertElement(540, 20 * i + 60, MapEntity.BRICK, 3);
		}
		dao.insertElement(290, 290, MapEntity.BRICK, 3);
		dao.insertElement(310, 290, MapEntity.BRICK, 3);
		dao.insertElement(290, 310, MapEntity.BRICK, 3);
		dao.insertElement(310, 310, MapEntity.BRICK, 3);
	}

	public void createMap4(){
		MapDao dao = new MapDao();
		for (int i = 0; i < 25; i++) {
			if (i == 11 || i == 12 || i == 13)
				continue;
			dao.insertElement(20 * i + 60, 60, MapEntity.WATER, 4);
		}
		for (int i = 0; i < 25; i++) {
			if (i == 11 || i == 12 || i == 13)
				continue;
			dao.insertElement(20 * i + 60, 460, MapEntity.WATER, 4);
		}
		for (int i = 0; i < 25; i++) {
			if (i == 11 || i == 12 || i == 13)
				continue;
			dao.insertElement(20 * i + 60, 140, MapEntity.WATER, 4);
		}
		for (int i = 0; i < 25; i++) {
			if (i == 11 || i == 12 || i == 13)
				continue;
			dao.insertElement(20 * i + 60, 540, MapEntity.WATER, 4);
		}
		for (int i = 0; i < 25; i++) {
			if (i == 11 || i == 12 || i == 13)
				continue;
			dao.insertElement(60, 20 * i + 60, MapEntity.WATER, 4);
		}
		for (int i = 0; i < 25; i++) {
			if (i == 11 || i == 12 || i == 13)
				continue;
			dao.insertElement(540, 20 * i + 60, MapEntity.WATER, 4);
		}
		dao.insertElement(290, 290, MapEntity.WATER, 4);
		dao.insertElement(310, 290, MapEntity.WATER, 4);
		dao.insertElement(290, 310, MapEntity.WATER, 4);
		dao.insertElement(310, 310, MapEntity.WATER, 4);
	}
	
	/**
	 * 根据玩家名称查出玩家的唯一id
	 * 
	 * @param name
	 * @return
	 */
	public int selectPlayerId(String name) {
		log.info("SELECT ID BY NAME->name："+name);
		String sql = "select id from player where name=?";
		Connection conn = DBHelper.getConnection();
		PreparedStatement state;
		try {
			state = conn.prepareStatement(sql);
			state.setString(1, name);
			ResultSet rs = state.executeQuery();
			if (rs.next()) {
				return rs.getInt("id");
			}
		} catch (SQLException e) {
			e.printStackTrace();
			log.error(e.toString());;
		}
		return -1;
	}

	/**
	 * 获取对应地图的钢铁和水的信息
	 * 
	 * @param mapId
	 * @return
	 */
	public List<MapEntity> selectIronAndWater(int mapId) {
		log.info("SELECT IRON AND WATER->mapId:"+mapId);
		List<MapEntity> list = new ArrayList<>();
		String sql = "select * from map_element where map_id=? and (type=2 or type=3);";
		Connection conn = DBHelper.getConnection();
		PreparedStatement state;
		try {
			state = conn.prepareStatement(sql);
			state.setInt(1, mapId);
			ResultSet rs = state.executeQuery();
			while (rs.next()) {
				MapEntity entity = new MapEntity(rs.getInt("x"), rs.getInt("y"), rs.getInt("type"),
						rs.getInt("map_id"));
				list.add(entity);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			log.error(e.toString());
		}
		return list;

	}

	/**
	 * 获取用户的保存进度时的砖块、坦克位置信息
	 * 
	 * @param mapId
	 * @param playerId
	 * @return
	 */
	public List<MapEntity> selectStorage(int mapId, int playerId) {
		log.info("LOAD STORAGE->mapId:"+mapId+",playerId:"+playerId);
		List<MapEntity> list = new ArrayList<>();
		String sql = "select * from player_progress where map_id=? and player_id=?;";
		Connection conn = DBHelper.getConnection();
		PreparedStatement state;
		try {
			state = conn.prepareStatement(sql);
			state.setInt(1, mapId);
			state.setInt(2, playerId);
			ResultSet rs = state.executeQuery();
			while (rs.next()) {
				MapEntity entity = new MapEntity(rs.getInt("x"), rs.getInt("y"), rs.getInt("type"),
						rs.getInt("map_id"));
				list.add(entity);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			log.error(e.toString());
		}
		return list;

	}

	/**
	 * 
	 * 保存地图快照信息，因为钢铁和水不会发生变化，因此不需要进行保存
	 * 
	 * @param resource
	 * @param playerId
	 */
	public boolean saveProgress(GameResource resource, int playerId) {
		log.info("SAVE PROGRESS->playerId:"+playerId);
		StringBuilder sb=new StringBuilder();
		String sql = "insert into player_progress(x,y,type,map_id,player_id) values(?,?,?,?,?);";
		Connection conn = DBHelper.getConnection();
		PreparedStatement state;
		try {
			conn.setAutoCommit(false);
			state = conn.prepareStatement(sql);
			for (Brick brick : resource.getBricks()) {
				state.setInt(1, brick.getX());
				state.setInt(2, brick.getY());
				state.setInt(3, MapEntity.BRICK);
				state.setInt(4, resource.getMapId());
				state.setInt(5, playerId);
				state.addBatch();
				sb.append("[");
				sb.append("x="+brick.getX()+",");
				sb.append("y="+brick.getY()+",");
				sb.append("type=brick,");
				sb.append("mapId="+resource.getMapId()+",");
				sb.append("playerId="+playerId);
				sb.append("]");
			}
			for (Tank tank : resource.getPlayerTanks().values()) {
				state.setInt(1, tank.getX());
				state.setInt(2, tank.getY());
				state.setInt(3, MapEntity.PLAYER_TANK);
				state.setInt(4, resource.getMapId());
				state.setInt(5, playerId);
				state.addBatch();
				sb.append("[");
				sb.append("x="+tank.getX()+",");
				sb.append("y="+tank.getY()+",");
				sb.append("type=tank,");
				sb.append("mapId="+resource.getMapId()+",");
				sb.append("playerId="+playerId);
				sb.append("]");
			}
			for (RobotTank robot : resource.getRobotTanks().values()) {
				state.setInt(1, robot.getX());
				state.setInt(2, robot.getY());
				state.setInt(3, MapEntity.ROBOT);
				state.setInt(4, resource.getMapId());
				state.setInt(5, playerId);
				state.addBatch();
				sb.append("[");
				sb.append("x="+robot.getX()+",");
				sb.append("y="+robot.getY()+",");
				sb.append("type=robotTank,");
				sb.append("mapId="+resource.getMapId()+",");
				sb.append("playerId="+playerId);
				sb.append("]");
			}
			int[] rs = state.executeBatch();
			conn.commit();
			conn.setAutoCommit(true);
			log.info("INSERT INTO player_progress->"+sb);
			for (int b : rs) {
				if (b < 1) {
					return false;
				}
			}
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			log.error(e.toString());
		}

		return false;
	}

	public boolean deleteProgress(int mapId, int playerId) {
		log.info("DELETE PROGRESS->mapId:"+mapId+",playerId:"+playerId);
		String sql = "delete from player_progress where map_id=? and player_id=?;";
		Connection conn = DBHelper.getConnection();
		PreparedStatement state;
		try {
			state = conn.prepareStatement(sql);
			state.setInt(1, mapId);
			state.setInt(2, playerId);
			return state.execute();
		} catch (SQLException e) {
			e.printStackTrace();
			log.error(e.toString());
		}
		return false;
	}

	/**
	 * 获取所有地图的信息
	 * 
	 * @return
	 */
	public List<Chapter> selectAllMapInfo() {
		List<Chapter> list = new ArrayList<>();
		String sql = "select * from map;";
		Connection conn = DBHelper.getConnection();
		PreparedStatement state;
		try {
			state = conn.prepareStatement(sql);
			ResultSet rs = state.executeQuery();
			while (rs.next()) {
				Chapter mapInfo = new Chapter(rs.getInt("id"), rs.getString("name"));
				list.add(mapInfo);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	/**
	 * 玩家在对应地图是否有存档
	 * 
	 * @param mapId
	 * @param playerId
	 */
	public boolean isStore(int mapId, int playerId) {

		String sql = "select count(*) from player_progress where map_id=? and player_id=?;";
		Connection conn = DBHelper.getConnection();
		PreparedStatement state;
		try {
			state = conn.prepareStatement(sql);
			state.setInt(1, mapId);
			state.setInt(2, playerId);
			ResultSet rs = state.executeQuery();
			rs.next();
			if (rs.getInt(1) > 0) {
				return true;
			} else {
				return false;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
}
