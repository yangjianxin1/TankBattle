package com.tank.entity;

import java.io.Serializable;

public class Water extends Element implements Serializable{

	public Water(Integer x, Integer y) {
		super(x, y);
		this.setType(WATER);
		this.setWidth(20);
		this.setHeight(20);
	}

}
