package com.mar21.au.entity.mob;

import com.mar21.au.entity.Entity;
import com.mar21.au.entity.projectile.HoleProjectile;
import com.mar21.au.entity.projectile.Projectile;
import com.mar21.au.graphics.Screen;

public abstract class Mob extends Entity {

	protected int dir = 0;
	protected int rate = 0;
	protected double walkspeed = 0;

	public void move(double xa, double ya) {
		if (xa != 0 && ya != 0) {
			move(xa, 0);
			move(0, ya);
			return;
		}

		if (xa > 0) dir = 1;
		if (xa < 0) dir = 3;
		if (ya > 0) dir = 2;
		if (ya < 0) dir = 0;

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
		if (value < 0) return -1;
		return 1;
	}

	public abstract void update();

	protected void updatesh(int skip) {
		if (rate > 0) rate -= skip;
	}

	protected void resetsh(int rate) {
		this.rate = rate;
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
			// double xt = ((x + xa) + c % 2 * 13 - 7) / 16;
			// double yt = ((y + ya) + c / 2 * 15 + 0) / 16;
			double xt = ((x + xa) + c % 2 * 16) / 16;
			double yt = ((y + ya) + c / 2 * 16) / 16;
			int ix = (int) Math.ceil(xt);
			int iy = (int) Math.ceil(yt);
			if (c % 2 == 0) ix = (int) Math.floor(xt);
			if (c / 2 == 0) iy = (int) Math.floor(yt);
			if (level.getTile(ix, iy).solid()) solid = true;
		}
		return solid;
	}

	public abstract void render(Screen screen);
}
