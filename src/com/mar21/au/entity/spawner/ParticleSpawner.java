package com.mar21.au.entity.spawner;

import com.mar21.au.entity.particle.Particle;
import com.mar21.au.level.Level;

public class ParticleSpawner extends Spawner {

	private int life;

	public ParticleSpawner(int x, int y, int life, int a, Level level) {
		super(x, y, Type.PARTICLE, a, level);
		this.life = life;
		for (int i = 0; i < a; i++) {
			level.add(new Particle(x, y, life));
		}
	}

}
