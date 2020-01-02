package com.tank.response;

import java.io.Serializable;
import java.util.List;

import com.tank.entity.Bomb;
import com.tank.entity.Brick;
import com.tank.entity.Tank;

/**
 * 地图变化信息
 * 
 * @author young
 *
 */
public class MapChangeResponse implements Serializable {
	// 产生爆炸的坐标
	private List<Bomb> bombs;
	// 被破坏的砖头
	private List<Brick> bricks;
	// 坦克
	private Tank tank;

	
}
