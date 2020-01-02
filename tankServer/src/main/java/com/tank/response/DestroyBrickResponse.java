package com.tank.response;

import java.io.Serializable;

import com.tank.request.DestroyBrickRequest;

public class DestroyBrickResponse implements Serializable {

	private int x;
	private int y;

	public DestroyBrickResponse(DestroyBrickRequest request) {
		this.x = request.getX();
		this.y = request.getY();
	}

	public DestroyBrickResponse() {

	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

}
