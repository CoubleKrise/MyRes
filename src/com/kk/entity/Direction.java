package com.kk.entity;

/**
 * 枚举：获取方向
 * @author Couble Krise
 */
public enum Direction {
	STATIC(0, 0), //
	WEST(-1, 0), //
	EAST(1, 0), //
	SOUTH(0, 1), //
	NORTH(0, -1), //
	SOUTH_EAST(1, 1), //
	SOUTH_WEST(-1, 1), //
	NORTH_EAST(1, -1), //
	NORTH_WEST(-1, -1);//

	private int dx;
	private int dy;

	/**
	 * @return
	 */
	public int getDx() {
		return dx;
	}

	/**
	 * @return
	 */
	public int getDy() {
		return dy;
	}

	/**
	 * @param dx
	 * @param dy
	 */
	private Direction(int dx, int dy) {
		this.dx = dx;
		this.dy = dy;
	}

}
