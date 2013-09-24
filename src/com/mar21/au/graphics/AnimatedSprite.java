package com.mar21.au.graphics;

import com.mar21.au.entity.Entity;

public class AnimatedSprite extends Sprite {

	private int frame = 0;
	private Sprite sprite;
	private int rate = 5;
	private int length = -1;
	private long tick = 0;

	public AnimatedSprite(SpriteSheet sheet, int lenght) {
		super(sheet);
		sprite = sheet.getSprites()[0];
		this.length = lenght;

	}

	public void update(Entity e) {
		if (e.isMoving()) {
			tick++;
			if (tick > 20) {
				if (frame >= length - 1) frame = 0;
				else frame++;
				sprite = sheet.getSprites()[frame];
				tick = 0;
			}
		} else {
			sprite = sheet.getSprites()[0];
		}
	}

	public Sprite getSprite() {
		return sprite;
	}

	public void setFrameRate(int frames) {
		rate = frames;
	}

	public void setFrame(int i) {
		if (i > sheet.getSprites().length - 1) {
			System.err.println("Out of bounds");
			return;
		}
		sprite = sheet.getSprites()[i];
	}

}
