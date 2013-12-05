package com.mar21.au.level;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.mar21.au.entity.Entity;
import com.mar21.au.entity.mob.Player;
import com.mar21.au.entity.particle.Particle;
import com.mar21.au.entity.projectile.Projectile;
import com.mar21.au.graphics.Screen;
import com.mar21.au.level.tile.Tile;
import com.mar21.au.util.Vector2i;

public class Level {

	protected int width, height;
	protected int[] tilesInt;
	protected int[] tiles;

	private List<Entity> entities = new ArrayList<Entity>();
	private List<Projectile> projectiles = new ArrayList<Projectile>();
	private List<Particle> particles = new ArrayList<Particle>();

	private List<Player> players = new ArrayList<Player>();

	private Comparator<Node> nodeSorter = new Comparator<Node>() {
		public int compare(Node n0, Node n1) {
			if (n1.fCost < n0.fCost) return +1;
			if (n1.fCost > n0.fCost) return -1;
			return 0;
		}
	};

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

	public List<Node> findPath(Vector2i start, Vector2i goal) {
		List<Node> openList = new ArrayList<Node>();
		List<Node> closedList = new ArrayList<Node>();
		Node current = new Node(start, null, 0, getDistance(start, goal));
		openList.add(current);
		while (openList.size() > 0) {
			Collections.sort(openList, nodeSorter);
			current = openList.get(0);
			if (current.tile.equals(goal)) {
				List<Node> path = new ArrayList<Node>();
				while (current.parent != null) {
					path.add(current);
					current = current.parent;
				}
				openList.clear();
				closedList.clear();
				return path;
			}
			openList.remove(current);
			closedList.add(current);
			for (int i = 0; i < 9; i++) {
				if (i == 4) continue;
				int x = current.tile.getX();
				int y = current.tile.getY();
				int xi = (i % 3) - 1;
				int yi = (i / 3) - 1;
				Tile at = getTile(x + xi, y + yi);
				if (at == null) continue;
				if (at.solid()) continue;
				Vector2i a = new Vector2i(x + xi, y + yi);
				double gCost = current.gCost + getDistance(current.tile, a);
				double hCost = getDistance(a, goal);
				Node node = new Node(a, current, gCost, hCost);
				if (vecInList(closedList, a) && gCost >= node.gCost) continue;
				if (!vecInList(openList, a) || gCost < node.gCost) openList.add(node);
			}
		}
		closedList.clear();
		return null;
	}

	private boolean vecInList(List<Node> list, Vector2i vector) {
		for (Node n : list) {
			if (n.tile.equals(vector)) return true;
		}
		return false;
	}

	private double getDistance(Vector2i tile, Vector2i goal) {
		double dx = tile.getX() - goal.getX();
		double dy = tile.getX() - goal.getY();
		return Math.sqrt(dx * dx + dy * dy);
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

	public long getFCount() {
		return players.size();
	}

	public void update() {
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
		remove();
	}

	private void remove() {
		for (int i = 0; i < entities.size(); i++) {
			if (entities.get(i).isRemoved()) entities.remove(i);
		}
		for (int i = 0; i < projectiles.size(); i++) {
			if (projectiles.get(i).isRemoved()) projectiles.remove(i);
		}
		for (int i = 0; i < particles.size(); i++) {
			if (particles.get(i).isRemoved()) particles.remove(i);
		}
		for (int i = 0; i < players.size(); i++) {
			if (players.get(i).isRemoved()) players.remove(i);
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

	public void render(int xscroll, int yscroll, Screen screen) {
		screen.setOffset(xscroll, yscroll);
		int x0 = xscroll >> 4;
		int x1 = (xscroll + screen.width + 16) >> 4;
		int y0 = yscroll >> 4;
		int y1 = (yscroll + screen.width + 16) >> 4;

		for (int y = y0; y < y1; y++) {
			for (int x = x0; x < x1; x++) {
				if (getTile(x, y).layer()) {
					if (getTile(x - 1, y) != Tile.voidTile && getTile(x - 1, y) != getTile(x, y)) {
						getTile(x - 1, y).render(x, y, screen);
					} else if (getTile(x + 1, y) != Tile.voidTile && getTile(x + 1, y) != getTile(x, y)) {
						getTile(x + 1, y).render(x, y, screen);
					} else if (getTile(x, y - 1) != Tile.voidTile && getTile(x, y - 1) != getTile(x, y)) {
						getTile(x, y - 1).render(x, y, screen);
					} else if (getTile(x, y + 1) != Tile.voidTile && getTile(x, y + 1) != getTile(x, y)) {
						getTile(x, y + 1).render(x, y, screen);
					} else if (getTile(x + 2, y) != Tile.voidTile && getTile(x + 2, y) != getTile(x, y)) {
						getTile(x + 2, y).render(x, y, screen);
					} else if (getTile(x - 2, y) != Tile.voidTile && getTile(x - 2, y) != getTile(x, y)) {
						getTile(x - 2, y).render(x, y, screen);
					} else if (getTile(x, y - 2) != Tile.voidTile && getTile(x, y - 2) != getTile(x, y)) {
						getTile(x, y - 2).render(x, y, screen);
					} else if (getTile(x, y + 2) != Tile.voidTile && getTile(x, y + 2) != getTile(x, y)) {
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
		if (x < 0 || y < 0 || x >= width || y >= height) { return Tile.voidTile; }
		if (tiles[x + y * width] == 0xFFFF0000) { return Tile.grass; }
		if (tiles[x + y * width] == 0xFFFFFF00) { return Tile.grass1; }
		if (tiles[x + y * width] == 0xFF00FFFF) { return Tile.grass2; }
		if (tiles[x + y * width] == 0xFF000000) { return Tile.rock; }
		if (tiles[x + y * width] == 0xFFFF6A00) { return Tile.planks; }
		if (tiles[x + y * width] == 0xFFFFD800) { return Tile.doors; }
		return Tile.voidTile;
	}

	public boolean tileCollision(double x, double y, double xa, double ya, int size) {
		boolean solid = false;
		for (int c = 0; c < 4; c++) {
			int xt = (((int) x + (int) xa) + c % 2 * size / 10) / 16;
			int yt = (((int) y + (int) ya) + c / 2 * size / 6 + 2) / 16;
			if (getTile(xt, yt).solid()) solid = true;
		}
		return solid;
	}

	public boolean pixelCollision(int x, int y, int size, int xo, int yo) {
		boolean solid = false;
		for (int c = 0; c < 4; c++) {
			int xt = (x - c % 2 * size + xo) >> 4;
			int yt = (y - c / 2 * size + yo) >> 4;
			if (getTile(xt, yt).solid()) solid = true;
		}
		return solid;
	}

	public List<Entity> getEntities(Entity e, int radius) {
		List<Entity> result = new ArrayList<Entity>();
		int a = (int) e.getX();
		int b = (int) e.getY();
		for (int i = 0; i < entities.size(); i++) {
			Entity f = entities.get(i);
			int x = (int) f.getX();
			int y = (int) f.getY();
			int dx = Math.abs(x - a);
			int dy = Math.abs(y - b);
			double dist = Math.sqrt((dx * dx) + (dy * dy));
			if (dist <= radius) {
				result.add(f);
			}
		}
		return result;
	}

	public List<Player> getPlayers(Entity e, int radius) {
		List<Player> result = new ArrayList<Player>();
		int a = (int) e.getX();
		int b = (int) e.getY();
		for (int i = 0; i < players.size(); i++) {
			Player f = players.get(i);
			int x = (int) f.getX();
			int y = (int) f.getY();
			int dx = Math.abs(x - a);
			int dy = Math.abs(y - b);
			double dist = Math.sqrt((dx * dx) + (dy * dy));
			if (dist <= radius) {
				result.add(f);
			}
		}
		return result;
	}

}
