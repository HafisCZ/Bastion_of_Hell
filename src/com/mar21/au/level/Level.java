package com.mar21.au.level;

import java.util.ArrayList;
import java.util.List;

import com.mar21.au.entity.Entity;
import com.mar21.au.entity.projectile.Projectile;
import com.mar21.au.graphics.Screen;
import com.mar21.au.level.tile.Tile;

public class Level {

	protected int width, height;
	protected int[] tilesInt;
	protected int[] tiles;

	private List<Entity> entities = new ArrayList<Entity>();
	private List<Projectile> projectiles = new ArrayList<Projectile>();

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

	public List<Projectile> getProjectiles() {
		return projectiles;
	}

	protected void generateLevel() {
	}

	protected void loadLevel(String path) {

	}

	public long getPCount() {
		return projectiles.size();
	}

	public void update() {
		for (int i = 0; i < entities.size(); i++) {
			entities.get(i).update();
		}
		for (int i = 0; i < projectiles.size(); i++) {
			projectiles.get(i).update();
		}
	}

	public void add(Entity e) {
		entities.add(e);
	}

	public void addProjectile(Projectile p) {
		p.init(this);
		projectiles.add(p);
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

		for (int i = 0; i < entities.size(); i++) {
			entities.get(i).render(screen);
		}
		for (int i = 0; i < projectiles.size(); i++) {
			projectiles.get(i).render(screen);
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
		if (tiles[x + y * width] == 0xFF000000) {
			return Tile.rock;
		}
		if (tiles[x + y * width] == 0xFFFF6A00) {
			return Tile.planks;
		}
		if (tiles[x + y * width] == 0xFFFFD800) {
			return Tile.doors;
		}
		return Tile.voidTile;
	}

	public boolean tileCollision(double x, double y, double xa, double ya,
			int size) {
		boolean solid = false;
		for (int c = 0; c < 4; c++) {
			int xt = (((int) x + (int) xa) + c % 2 * size / 10) / 16;
			int yt = (((int) y + (int) ya) + c / 2 * size / 6 + 2) / 16;
			if (getTile(xt, yt).solid())
				solid = true;
		}
		return solid;
	}

}
