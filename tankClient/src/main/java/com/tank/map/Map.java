package com.tank.map;

import java.io.Serializable;
import java.util.Vector;
import java.util.concurrent.CopyOnWriteArrayList;

import com.tank.entity.Brick;
import com.tank.entity.Iron;
import com.tank.entity.Water;

public class Map implements Serializable{
	private CopyOnWriteArrayList<Brick> bricks;
	private CopyOnWriteArrayList<Iron> irons;
	private CopyOnWriteArrayList<Water> waters;

	public Map() {
		bricks = new CopyOnWriteArrayList<Brick>();
		irons = new CopyOnWriteArrayList<Iron>();
		waters = new CopyOnWriteArrayList<Water>();
	}

	public CopyOnWriteArrayList<Brick> getBricks() {
		return bricks;
	}

	public void setBricks(CopyOnWriteArrayList<Brick> bricks) {
		this.bricks = bricks;
	}

	public CopyOnWriteArrayList<Iron> getIrons() {
		return irons;
	}

	public void setIrons(CopyOnWriteArrayList<Iron> irons) {
		this.irons = irons;
	}

	public CopyOnWriteArrayList<Water> getWaters() {
		return waters;
	}

	public void setWaters(CopyOnWriteArrayList<Water> waters) {
		this.waters = waters;
	}

}
