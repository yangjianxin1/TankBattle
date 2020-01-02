package com.tank.response;

import java.io.Serializable;
import java.util.List;

import com.tank.entity.Chapter;

/**
 * 包含所有地图的名称和id
 * @author Administrator
 *
 */
public class ChapterResponse implements Serializable {
	List<Chapter> mapInfo;

	public ChapterResponse(List<Chapter> mapInfo) {
		this.mapInfo = mapInfo;
	}

	public List<Chapter> getMapInfo() {
		return mapInfo;
	}

	public void setMapInfo(List<Chapter> mapInfo) {
		this.mapInfo = mapInfo;
	}

}
