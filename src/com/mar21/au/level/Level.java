package com.mar21.au.level;

import com.mar21.au.graphics.Screen;
import com.mar21.au.level.tile.Tile;

public class Level {

	protected int width, height;
	protected int[] tilesInt;
	protected int[] tiles;

	public Level(int width, int height) {
		this.width = width;
		this.height = height;
		tilesInt = new int[width * height];
		System.out.println("Generating level ...");
		generateLevel();
	}

	public Level(String path) {
		loadLevel(path);
		generateLevel();
	}

	protected void generateLevel() {
	}

	protected void loadLevel(String path) {

	}

	public void update() {

	}

	private void time() {

	}

	public void render(int xscroll, int yscroll, Screen screen) {
		screen.setOffset(xscroll, yscroll);
		int x0 = xscroll >> 4;
		int x1 = (xscroll + screen.width + 16) >> 4;
		int y0 = yscroll >> 4;
		int y1 = (yscroll + screen.width + 16) >> 4;

		for (int y = y0; y < y1; y++) {
			for (int x = x0; x < x1; x++) {
				if (getTile(x, y).layer()) {
					if (getTile(x - 1, y) != Tile.voidTile
							&& getTile(x - 1, y) != getTile(x, y)) {
						getTile(x - 1, y).render(x, y, screen);
					} else if (getTile(x + 1, y) != Tile.voidTile
							&& getTile(x + 1, y) != getTile(x, y)) {
						getTile(x + 1, y).render(x, y, screen);
					} else if (getTile(x, y - 1) != Tile.voidTile
							&& getTile(x, y - 1) != getTile(x, y)) {
						getTile(x, y - 1).render(x, y, screen);
					} else if (getTile(x, y + 1) != Tile.voidTile
							&& getTile(x, y + 1) != getTile(x, y)) {
						getTile(x, y + 1).render(x, y, screen);
					} else if (getTile(x + 2, y) != Tile.voidTile
							&& getTile(x + 2, y) != getTile(x, y)) {
						getTile(x + 2, y).render(x, y, screen);
					} else if (getTile(x - 2, y) != Tile.voidTile
							&& getTile(x - 2, y) != getTile(x, y)) {
						getTile(x - 2, y).render(x, y, screen);
					} else if (getTile(x, y - 2) != Tile.voidTile
							&& getTile(x, y - 2) != getTile(x, y)) {
						getTile(x, y - 2).render(x, y, screen);
					} else if (getTile(x, y + 2) != Tile.voidTile
							&& getTile(x, y + 2) != getTile(x, y)) {
						getTile(x, y + 2).render(x, y, screen);
					} else {
						Tile.voidTile.render(x, y, screen);
					}
				}
				getTile(x, y).render(x, y, screen);
			}
		}
	}

	public Tile getTile(int x, int y) {
		if (x < 0 || y < 0 || x >= width || y >= height) {
			return Tile.voidTile;
		}
		if (tiles[x + y * width] == 0xFFFF0000) {
			return Tile.grass;
		}
		if (tiles[x + y * width] == 0xFFFFFF00) {
			return Tile.grass1;
		}
		if (tiles[x + y * width] == 0xFF00FFFF) {
			return Tile.grass2;
		}
		if (tiles[x + y * width] == 0xFF00FF00) {
			return Tile.footprint;
		}
		return Tile.voidTile;
	}

}
