package com.tank.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.tank.dao.MapDao;
import com.tank.dao.entity.MapEntity;
import com.tank.entity.Brick;
import com.tank.entity.Iron;
import com.tank.entity.Chapter;
import com.tank.entity.RobotTank;
import com.tank.entity.Tank;
import com.tank.entity.Water;
import com.tank.response.InitialMapResponse;
import com.tank.response.ChapterResponse;

public class MapService {
	private MapDao mapDao = new MapDao();



	/**
	 * 全部地图的描述信息
	 * 
	 * @return
	 */
	public ChapterResponse selectChapters() {
		List<Chapter> list = mapDao.selectAllMapInfo();
		ChapterResponse response = new ChapterResponse(list);
		return response;
	}

	

}
