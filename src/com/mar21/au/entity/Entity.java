package com.mar21.au.entity;

import java.util.Random;

import com.mar21.au.graphics.Screen;
import com.mar21.au.level.Level;

public class Entity{

	public int x, y;
	private boolean removed = false;
	protected Level level;
	protected final Random random = new Random();

	public void update() {

	}

	public void render(Screen screen) {

	}

	public void remove() {
		removed = true;
	}

	public boolean isRemoved() {
		return removed;
	}
	
	public void init(Level level) {
		this.level = level;
	}

}
