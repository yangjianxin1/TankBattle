package com.tank.response;

import java.io.Serializable;

/**
 * 保存进度的响应消息
 * 
 * @author Administrator
 *
 */
public class SaveProgressResponse implements Serializable {
	private State state;

	public static enum State {
		Success, Failure;
	}

	public State getState() {
		return state;
	}

	public void setState(State state) {
		this.state = state;
	}

}
