package com.tank.entity;

import java.io.Serializable;

public class Brick extends Element implements Serializable{

	public Brick(Integer x, Integer y) {
		super(x, y);
		this.setType(BRICK);
		this.setWidth(20);
		this.setHeight(20);
	}

}
