package com.mar21.au.entity.mob;

import java.util.List;

import com.mar21.au.entity.Entity;
import com.mar21.au.entity.projectile.HoleProjectile;
import com.mar21.au.entity.projectile.Projectile;
import com.mar21.au.graphics.Screen;
import com.mar21.au.graphics.Sprite;
import com.mar21.au.util.Vector2i;

public abstract class Mob extends Entity {

	protected int dir = 0;
	protected int rate = 0;
	protected double walkspeed = 0;
	protected Sprite sprite;

	private Entity lock_entity = null;

	public void move(double xa, double ya) {
		if (xa != 0 && ya != 0) {
			move(xa, 0);
			move(0, ya);
			return;
		}

		if (xa > 0)
			dir = 1;
		if (xa < 0)
			dir = 3;
		if (ya > 0)
			dir = 2;
		if (ya < 0)
			dir = 0;

		while (xa != 0) {
			if (Math.abs(xa) > 1) {
				if (!collision(abs(xa), ya)) {
					this.x += abs(xa);
				}
				xa -= abs(xa);
			} else {
				if (!collision(abs(xa), ya)) {
					this.x += xa;
				}
				xa = 0;
			}
		}

		while (ya != 0) {
			if (Math.abs(ya) > 1) {
				if (!collision(xa, abs(ya))) {
					this.y += abs(ya);
				}
				ya -= abs(ya);
			} else {
				if (!collision(xa, abs(ya))) {
					this.y += ya;
				}
				ya = 0;
			}
		}

	}

	private int abs(double value) {
		if (value < 0)
			return -1;
		return 1;
	}

	public abstract void update();

	protected void updatesh(int skip) {
		if (rate > 0)
			rate -= skip;
	}

	protected void resetsh(int rate) {
		this.rate = rate;
	}

	protected void shootRandom(int range, int tick) {
		if (tick % (60 + random.nextInt(61)) == 0) {
			List<Entity> entities = level.getEntities(this, range);
			entities.add(level.getClientPlayer());

			int index = random.nextInt(entities.size());
			lock_entity = entities.get(index);
		}

		if (lock_entity != null) {
			double dx = lock_entity.getX() - x;
			double dy = lock_entity.getY() - y;
			double dir = Math.atan2(dy, dx);
			shoot(x + 16, y + 16, dir, 0);
		}
	}

	protected void shootClosest(int range) {
		List<Entity> entities = level.getEntities(this, range);
		entities.add(level.getClientPlayer());
		double min = 0;
		Entity closest = null;
		for (int i = 0; i < entities.size(); i++) {
			Entity e = entities.get(i);
			double distance = Vector2i.getDistance(new Vector2i(x, y),
					new Vector2i(e.getX(), e.getY()));
			if (i == 0 || distance < min) {
				min = distance;
				closest = e;
			}
		}
		if (closest != null) {
			double dx = closest.getX() - x;
			double dy = closest.getY() - y;
			double dir = Math.atan2(dy, dx);
			shoot(x + 16, y + 16, dir, 0);
		}
	}

	protected void shoot(double x, double y, double dir, int index) {
		if (index == 0) {
			Projectile p = new HoleProjectile(x, y, dir);
			level.add(p);
		} else if (index == 1) {
			Projectile p = new HoleProjectile(x, y, dir);
			level.add(p);
		}

	}

	private boolean collision(double xa, double ya) {
		boolean solid = false;
		for (int c = 0; c < 4; c++) {
			double xt = ((x + xa) - c % 2 * 15) / 16;
			double yt = ((y + ya) - c / 2 * 15) / 16;
			int ix = (int) Math.ceil(xt);
			int iy = (int) Math.ceil(yt);
			if (c % 2 == 0)
				ix = (int) Math.floor(xt);
			if (c / 2 == 0)
				iy = (int) Math.floor(yt);
			if (level.getTile(ix, iy).solid())
				solid = true;
		}
		return solid;
	}

	public abstract void render(Screen screen);
}
