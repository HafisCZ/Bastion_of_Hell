package com.mar21.au.entity.mob;

import java.util.List;

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
		this.walkspeed = 1;
		this.sprite = Sprite.player0;
	}

	private void move() {
		xa = 0;
		ya = 0;

		List<Player> p = level.getPlayers(this, 50);
		if (p.size() > 0) {
			Player player = p.get(0);
			if (x < player.getX() - 16) xa += walkspeed;
			if (x > player.getX() + 16) xa -= walkspeed;
			if (y < player.getY() - 16) ya += walkspeed;
			if (y > player.getY() + 16) ya -= walkspeed;
		}

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
		screen.renderMob((int) x - sprite.getW() / 2, (int) y - sprite.getH() / 2, sprite);
	}

}
