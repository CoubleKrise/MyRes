package com.kk.entity;

/**
 * 
 * @author Couble Krise
 */
public class Agent {
	//S-I-R
	private String state = State.S;

	/**
	 * @return
	 */
	public String getState() {
		return state;
	}

	/**
	 * @param state
	 */
	public void setState(String state) {
		this.state = state;
	}

	/**
	 * toString
	 * 
	 */
	@Override
	public String toString() {
		return "Agent:  state=" + state;
	}
}
