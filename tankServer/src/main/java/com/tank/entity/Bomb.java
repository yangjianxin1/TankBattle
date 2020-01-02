package com.tank.entity;

import java.io.Serializable;

/**
 * 炸弹
 * 
 * @author young
 *
 */
public class Bomb extends Element implements Serializable {

	public Bomb(Integer x, Integer y) {
		super(x, y);
	}

	/**
	 * 炸弹的生命值，在画面中展示的时间长短
	 */
	private Integer lifeValue = 30;

	public void lifeDown() {
		if (lifeValue > 0)
			lifeValue--;
	}

	public Integer getLifeValue() {
		return lifeValue;
	}

	public void setLifeValue(Integer lifeValue) {
		this.lifeValue = lifeValue;
	}

}
