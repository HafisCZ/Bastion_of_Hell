package com.mar21.au.entity.projectile;

import com.mar21.au.entity.spawner.ParticleSpawner;
import com.mar21.au.graphics.Screen;
import com.mar21.au.graphics.Sprite;

public class HoleProjectile extends Projectile {

	public static final int RATE = 10;

	public HoleProjectile(double x, double y, double dir) {
		super(x, y, dir);
		range = random.nextInt(100) + 150;
		dmg = 20;
		speed = 4;
		sprite = Sprite.p7;

		nx = speed * Math.cos(angle);
		ny = speed * Math.sin(angle);
	}

	public void update() {
		if (level.pixelCollision((int) (x + nx), (int) (y + ny), 8, 4, 5)) {
			level.add(new ParticleSpawner((int) x, (int) y, 44, 50, level));
			remove();
		}
		move();
	}

	protected void move() {
		x += nx;
		y += ny;
		if (distance() > range) remove();
	}

	private double distance() {
		double dist = 0;
		dist = Math.sqrt(Math.abs((xOrigin - x) * (xOrigin - x) + (yOrigin - y) * (yOrigin - y)));
		return dist;
	}

	public void render(Screen screen) {
		screen.renderTile((int) x - 8, (int) y - 6, sprite, true);
	}

}
