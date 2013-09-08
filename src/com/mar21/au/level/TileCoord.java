package com.mar21.au.level;

public class TileCoord {

	private int x, y;
	public final int TILE_SIZE = 16;

	public TileCoord(int x, int y) {
		this.x = x * TILE_SIZE;
		this.y = y * TILE_SIZE;
	}

	public int x() {
		return x;
	}

	public int y() {
		return y;
	}

	public int[] xy() {
		return new int[] { x, y };
	}

}
