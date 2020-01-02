package com.tank.entity;

import java.io.Serializable;

public class MyTank extends Tank implements Serializable{

	public MyTank(Integer x, Integer y, Integer direct,String tankId) {
		super(x, y, direct,tankId);
	}

}
