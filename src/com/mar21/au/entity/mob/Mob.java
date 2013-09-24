package com.mar21.au.entity.mob;

import com.mar21.au.entity.Entity;
import com.mar21.au.entity.projectile.HoleProjectile;
import com.mar21.au.entity.projectile.Projectile;
import com.mar21.au.graphics.Screen;

public abstract class Mob extends Entity {

	protected int dir = 0;

	public void move(int xa, int ya) {
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

		if (!collision(xa, ya)) {
			x += xa;
			y += ya;
		}
	}

	public abstract void update();

	protected void shoot(int x, int y, double dir) {
		Projectile p = new HoleProjectile(x, y, dir);
		level.add(p);
	}

	private boolean collision(int xa, int ya) {
		boolean solid = false;
		for (int c = 0; c < 4; c++) {
			int xt = ((x + xa) + c % 2 * 13 - 7) / 16;
			int yt = ((y + ya) + c / 2 * 15 + 0) / 16;
			if (level.getTile(xt, yt).solid())
				solid = true;
		}
		return solid;
	}

	public abstract void render(Screen screen);
}
