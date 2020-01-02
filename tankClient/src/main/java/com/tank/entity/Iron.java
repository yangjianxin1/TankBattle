package com.tank.entity;

import java.io.Serializable;

public class Iron extends Element implements Serializable{

	public Iron(Integer x, Integer y) {
		super(x, y);
		this.setType(IRON);
		this.setWidth(20);
		this.setHeight(20);
	}

}
