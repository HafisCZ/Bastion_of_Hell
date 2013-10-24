package com.mar21.au.entity.projectile;

import java.util.Random;

import com.mar21.au.entity.Entity;
import com.mar21.au.graphics.Sprite;

public abstract class Projectile extends Entity {

	protected final double xOrigin, yOrigin;
	protected double angle;
	protected Sprite sprite;
	protected double x, y;
	protected double nx, ny;
	protected double speed, range, dmg;

	protected final Random random = new Random();

	public Projectile(double x, double y, double dir) {
		xOrigin = x;
		yOrigin = y;
		angle = dir;
		this.x = x;
		this.y = y;
	}

	protected void move() {

	}
}
