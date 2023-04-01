package com.kk.entity;

import java.util.*;

/**
 * @author Couble Krise
 *
 */
public class Patch {
	//
	private int x;
	private int y;
	//
	private double scale;//城市规模
	private double res;//城市资源
	private double infecRate = 0.002;
	private double recovRate = 0.04;
	private List<Agent> agents;

	/**
	 * @return
	 */
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

	public double getScale() {
		return scale;
	}

	public void setScale(double scale) {
		this.scale = scale;
	}

	public double getRes() {
		return res;
	}

	public void setRes(double res) {
		this.res = res;
	}

	public double getInfecRate() {
		return infecRate;
	}

	public void setInfecRate(double infecRate) {
		this.infecRate = infecRate;
	}

	public double getRecovRate() {
		return recovRate;
	}

	public void setRecovRate(double recovRate) {
		this.recovRate = recovRate;
	}

	public List<Agent> getAgents() {
		return agents;
	}


	/**
	 * @param row
	 * @param col
	 * @param scale
	 * @param res
	 */
	public Patch(int row, int col, double scale, double res) {
		this.x = row;
		this.y = col;
		this.scale = scale;
		this.res = res;
		this.agents = new LinkedList<Agent>();
	}

}
