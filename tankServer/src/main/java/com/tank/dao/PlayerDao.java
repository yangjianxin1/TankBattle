package com.tank.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PlayerDao {
	
	private static Logger log = LoggerFactory.getLogger(PlayerDao.class);

	/**
	 * 判断某个玩家是否存在
	 * 
	 * @param name
	 * @return
	 */
	public boolean playerExist(String name) {
		String sql = "select count(*) from player where name=?;";
		Connection conn = DBHelper.getConnection();
		PreparedStatement state;
		try {
			state = conn.prepareStatement(sql);
			state.setString(1, name);
			ResultSet rs = state.executeQuery();
			rs.next();
			if (rs.getInt(1) == 1) {
				return true;
			} else {
				return false;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;

	}

	public boolean validatePassword(String name, String password) {
		String sql = "select password from player where name=?;";
		Connection conn = DBHelper.getConnection();
		PreparedStatement state;
		try {
			state = conn.prepareStatement(sql);
			state.setString(1, name);
			ResultSet rs = state.executeQuery();
			rs.next();
			if (rs.getString(1).equals(password)) {
				return true;
			} else {
				return false;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * 插入一个用户
	 * 
	 * @param name
	 * @param password
	 * @return
	 */
	public boolean insertPlayer(String name, String password) {
		log.info("insert into player->name:"+name+",password:"+password);
		String sql = "insert into player (name,password)VALUES(?,?);";
		Connection conn = DBHelper.getConnection();
		PreparedStatement state;
		try {
			state = conn.prepareStatement(sql);
			state.setString(1, name);
			state.setString(2, password);
			int count = state.executeUpdate();
			if (count == 1)
				return true;
			else
				return false;
		} catch (SQLException e) {
			e.printStackTrace();
			log.error(e.toString());
		}
		return false;
	}

	public static void main(String[] args) throws ClassNotFoundException, SQLException {
		boolean b = new PlayerDao().validatePassword("yang", "124");
		System.out.println(b);
	}

}
