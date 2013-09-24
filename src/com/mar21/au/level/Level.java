package com.mar21.au.level;

import java.util.ArrayList;
import java.util.List;

import com.mar21.au.entity.Entity;
import com.mar21.au.entity.mob.Player;
import com.mar21.au.entity.particle.Particle;
import com.mar21.au.entity.projectile.Projectile;
import com.mar21.au.graphics.Screen;
import com.mar21.au.level.tile.Tile;

public class Level {

	protected int width, height;
	protected int[] tilesInt;
	protected int[] tiles;

	private List<Entity> entities = new ArrayList<Entity>();
	private List<Projectile> projectiles = new ArrayList<Projectile>();
	private List<Particle> particles = new ArrayList<Particle>();

	private List<Player> players = new ArrayList<Player>();

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

	public List<Player> getPlayer() {
		return players;
	}

	public Player getPlayerAt(int i) {
		return players.get(i);
	}

	public Player getClientPlayer() {
		return players.get(0);
	}

	protected void generateLevel() {
	}

	protected void loadLevel(String path) {

	}

	public long getPCount() {
		return projectiles.size();
	}

	public long getRCount() {
		return particles.size();
	}

	public long getECount() {
		return entities.size();
	}

	public void update() {
		remove();
		for (int i = 0; i < entities.size(); i++) {
			entities.get(i).update();
		}
		for (int i = 0; i < projectiles.size(); i++) {
			projectiles.get(i).update();
		}
		for (int i = 0; i < particles.size(); i++) {
			particles.get(i).update();
		}
		for (int i = 0; i < players.size(); i++) {
			players.get(i).update();
		}
	}

	private void remove() {
		for (int i = 0; i < entities.size(); i++) {
			if (entities.get(i).isRemoved())
				entities.remove(i);
		}
		for (int i = 0; i < projectiles.size(); i++) {
			if (projectiles.get(i).isRemoved())
				projectiles.remove(i);
		}
		for (int i = 0; i < particles.size(); i++) {
			if (particles.get(i).isRemoved())
				particles.remove(i);
		}
		for (int i = 0; i < players.size(); i++) {
			if (players.get(i).isRemoved())
				players.remove(i);
		}
	}

	public void add(Entity e) {
		e.init(this);
		if (e instanceof Particle) {
			particles.add((Particle) e);
		} else if (e instanceof Projectile) {
			projectiles.add((Projectile) e);
		} else if (e instanceof Player) {
			players.add((Player) e);
		} else {
			entities.add(e);
		}
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
		for (int i = 0; i < particles.size(); i++) {
			particles.get(i).render(screen);
		}
		for (int i = 0; i < players.size(); i++) {
			players.get(i).render(screen);
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

	public boolean pixelCollision(int x, int y, int size, int xo, int yo) {
		boolean solid = false;
		for (int c = 0; c < 4; c++) {
			int xt = (x - c % 2 * size + xo) >> 4;
			int yt = (y - c / 2 * size + yo) >> 4;
			if (getTile(xt, yt).solid())
				solid = true;
		}
		return solid;
	}

}
