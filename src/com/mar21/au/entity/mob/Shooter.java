package com.mar21.au.entity.mob;

import java.util.List;

import com.mar21.au.entity.Entity;
import com.mar21.au.graphics.AnimatedSprite;
import com.mar21.au.graphics.Screen;
import com.mar21.au.graphics.Sprite;
import com.mar21.au.graphics.SpriteSheet;
import com.mar21.au.util.Debug;
import com.mar21.au.util.Vector2i;

public class Shooter extends Mob {

	private AnimatedSprite down = new AnimatedSprite(SpriteSheet.wanderer_down,
			3);
	private AnimatedSprite up = new AnimatedSprite(SpriteSheet.wanderer_up, 3);
	private AnimatedSprite left = new AnimatedSprite(SpriteSheet.wanderer_left,
			3);
	private AnimatedSprite right = new AnimatedSprite(
			SpriteSheet.wanderer_right, 3);

	private AnimatedSprite animSprite = down;

	private int time = 0;
	private int xa = 0, ya = 0;

	public Shooter(int x, int y) {
		this.x = x << 4;
		this.y = y << 4;
		this.sprite = Sprite.player0;
		this.walkspeed = 0;
	}

	public void update() {
		time++;

		if (time % (random.nextInt(50) + 30) == 0) {
			xa = random.nextInt(3) - 1;
			ya = random.nextInt(3) - 1;
			if (random.nextInt(4) == 0) {
				xa = 0;
				ya = 0;
			}
		}

		xa *= walkspeed;
		ya *= walkspeed;

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

		if (xa != 0 || ya != 0) {
			move(xa, ya);
			moving = true;
		} else {
			moving = false;
		}

		shootRandom(100, time);
	}

	public void render(Screen screen) {
		sprite = animSprite.getSprite();
		//Debug.drawRect(screen, 40, 40, 100, 40, 0xff00ff, true);
		screen.renderMob(x, y, sprite);
	}

}
