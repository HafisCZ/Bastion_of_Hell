package com.mar21.au.graphics;

public class AnimatedSprite extends Sprite {

	private int frame = 0;
	private Sprite sprite;
	private int rate = 5;
	private int length = -1;

	public AnimatedSprite(SpriteSheet sheet, int lenght) {
		super(sheet);
		sprite = sheet.getSprites()[0];
		this.length = lenght;
		
	}

	public void update() {
		if (frame >= length - 1)
			frame = 0;
		else
			frame++;
		sprite = sheet.getSprites()[frame];
	}

	public Sprite getSprite() {
		return sprite;
	}

	public void setFrameRate(int frames) {
		rate = frames;
	}

}
