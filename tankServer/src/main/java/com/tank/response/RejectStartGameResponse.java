package com.tank.response;

import java.io.Serializable;

/**
 * 游戏无法开始
 * 
 * @author young
 *
 */
public class RejectStartGameResponse implements Serializable {
	private RejectStartGameState state;

	// 游戏已开始，玩家数量不够
	public static enum RejectStartGameState {
		 playerNotEnough;
	}

	public RejectStartGameState getState() {
		return state;
	}

	public void setState(RejectStartGameState state) {
		this.state = state;
	}

}
