package com.tank.entity;

import java.io.Serializable;

/**
 * 地图的描述信息
 * 
 * @author Administrator
 *
 */
public class Chapter implements Serializable {

	private int id;
	private String name;

	public Chapter(int id, String name) {
		this.id = id;
		this.name = name;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
