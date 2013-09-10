package com.mar21.au.entity.spawner;

import com.mar21.au.entity.Entity;
import com.mar21.au.entity.particle.Particle;
import com.mar21.au.level.Level;

public class Spawner extends Entity {

	public enum Type {
		MOB, PARTICLE;
	}

	private Type type;

	public Spawner(int x, int y, Type t, int a, Level level) {
		this.x = x;
		this.y = y;
		this.type = t;
	}

}
