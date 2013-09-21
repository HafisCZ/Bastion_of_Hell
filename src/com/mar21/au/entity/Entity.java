package com.mar21.au.entity;

import java.util.Random;

import com.mar21.au.graphics.Screen;
import com.mar21.au.graphics.Sprite;
import com.mar21.au.level.Level;

public class Entity{

	public int x, y;
	protected Sprite sprite;
	private boolean removed = false;
	protected Level level;
	protected final Random random = new Random();

	
	public Sprite getSprite() {
		return sprite;
	}
	
	public Entity(){
	}
	
	public Entity(int x, int y, Sprite sprite) {
		this.x = x;
		this.y = y;
		this.sprite = sprite;
	}
	
	public void update() {

	}

	public void render(Screen screen) {
		if (sprite != null) screen.renderSprite(x, y, sprite, true);
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
