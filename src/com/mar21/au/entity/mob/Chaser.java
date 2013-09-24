package com.mar21.au.entity.mob;

import com.mar21.au.graphics.AnimatedSprite;
import com.mar21.au.graphics.Screen;
import com.mar21.au.graphics.Sprite;
import com.mar21.au.graphics.SpriteSheet;

public class Chaser extends Mob {

	private AnimatedSprite down = new AnimatedSprite(SpriteSheet.wanderer_down, 3);
	private AnimatedSprite up = new AnimatedSprite(SpriteSheet.wanderer_up, 3);
	private AnimatedSprite left = new AnimatedSprite(SpriteSheet.wanderer_left, 3);
	private AnimatedSprite right = new AnimatedSprite(SpriteSheet.wanderer_right, 3);
	private AnimatedSprite animSprite = down;
	private int xa, ya;

	public Chaser(int x, int y) {
		this.x = x << 4;
		this.y = y << 4;
		sprite = Sprite.player0;
	}

	private void move() {
		xa = 0;
		ya = 0;

		Player player = level.getClientPlayer();
		if (x < player.getX() - 16) xa++;
		if (x > player.getX() + 16) xa--;
		if (y < player.getY() - 16) ya++;
		if (y > player.getY() + 16) ya--;
		if (xa != 0 || ya != 0) {
			move(xa, ya);
			moving = true;
		} else {
			moving = false;
		}
	}

	public void update() {
		move();
		animSprite.update(this);

		if (ya < 0) {
			animSprite = up;
		} else if (ya > 0) {
			animSprite = down;
		}
		if (xa < 0) {
			animSprite = left;
		} else if (xa > 0) {
			animSprite = right;
		}
	}

	public void render(Screen screen) {
		sprite = animSprite.getSprite();
		screen.renderMob(x - sprite.getW() / 2, y - sprite.getH() / 2, sprite);
	}

}
